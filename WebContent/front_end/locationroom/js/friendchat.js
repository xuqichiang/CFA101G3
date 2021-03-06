let chatTmp = `<div class="chatWrap" id="chatWrap">
<div class="chat-title">聊聊</div>
<div class="flexbox">
    <div class="messages-block">
        <div id="statusOutput" class="statusOutput" data-mem_id=""></div>
        <div id="messagesArea" class="panel message-area">
            <ul id="area">
                <div class="area-msg" id="area-msg">
                    <h3>歡迎使用聊聊</h3>
                    <p>趕緊和一位會員聊聊天吧～</p>
                </div>
            </ul>
        </div>
        <div class="panel input-area">
            <input id="message" class="text-field" type="text" placeholder="輸入文字">
            <a href="javascript:void(0)" id="sendMessage"><i class="far fa-paper-plane"></i></a>
            <a href="javascript:void(0)" id="sendImg"><i class="far fa-images"></i></a>
            <input type="file" name="ChatFile" id="ChatFile">
        </div>
    </div>
    <div id="row">

    </div>
</div>
<div class="chat-close" id="chat-close">-</div>
</div>
<div class="chat-components" id="chat-components">
<i class="far fa-comments"></i>
<span>聊聊</span>
</div>`;
$('body').append(chatTmp);

var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var chat_mem_id; //登入會員ID
$.ajax({
    type: "post",
    url: webCtx + "/member/buyProfileServlet",
    dataType: "json",
    async: false,
    success: function(result) {
        chat_mem_id = result.mem_id;
    }
});
console.log(chat_mem_id);


//載入頁面時建立WebSocket連線
window.onload = function() {
        if (chat_mem_id != undefined) {
            connect();
        } else {
            $('#area-msg').html("<p>您尚未登入，請登入後在使用聊聊</p>")
        }
    }
    //離開頁面時關閉WebSocket連線
window.onunload = function() {
    if (chat_mem_id != undefined) {
        disconnect();
    }
}


var MyPoint = "/FriendWS/" + chat_mem_id;
var host = window.location.host;
var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
var statusOutput = document.getElementById("statusOutput");
var messagesArea = document.getElementById("messagesArea");
var self = chat_mem_id;
var webSocket;




//ws連線後主function
function connect() {
    // create a websocket
    webSocket = new WebSocket(endPointURL);

    webSocket.onopen = function(event) {
        console.log("Connect Success!");
        document.getElementById('sendMessage').disabled = false;
        // $('#chat-components').show();
    };

    webSocket.onmessage = function(event) {
        var jsonObj = JSON.parse(event.data);
        console.log(jsonObj);
        if ("open" === jsonObj.type) {
            refreshFriendList(jsonObj);
            $('#chat-components').show();
        } else if ("history" === jsonObj.type) {
            $('#area').html("");
            // 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
            var messages = JSON.parse(jsonObj.message);
            for (var i = 0; i < messages.length; i++) {
                var historyData = JSON.parse(messages[i]);
                var showMsg = historyData.message;
                var li = document.createElement('li');
                // 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
                historyData.sender == self ? li.className += 'me' : li.className += 'friend';
                li.innerHTML = showMsg;
                $('#area').append(li);
            }
            messagesArea.scrollTop = messagesArea.scrollHeight;
        } else if ("chat" === jsonObj.type) {
            var li = document.createElement('li');
            jsonObj.sender === self ? li.className += 'me' : li.className += 'friend';
            if (statusOutput.dataset.mem_id == jsonObj.sender || jsonObj.sender == self) { //如果切到對應的人才會顯示li或者是自己發送的訊息
                li.innerHTML = jsonObj.message;
                console.log(li);
                document.getElementById("area").appendChild(li);
                messagesArea.scrollTop = messagesArea.scrollHeight;
                var jsonObj = {
                    "type": "clearUnRead",
                    "sender": self,
                    "receiver": jsonObj.sender,
                    "message": ""
                };
                webSocket.send(JSON.stringify(jsonObj)); //如果雙方都在聊天則刪除未讀
            } else {
                var jsonObj = {
                    "type": "newMsg",
                    "sender": self,
                    "receiver": jsonObj.sender,
                    "message": ""
                };
                webSocket.send(JSON.stringify(jsonObj));
            }

        } else if ("newChat" === jsonObj.type) {
            refreshFriendList(jsonObj) //更新完列表後觸發抓取好友名字以取得歷史訊息事件
            $(`#friend${jsonObj.receiver}`).click();
        } else if ("newMsg" === jsonObj.type) {
            refreshFriendListByNewMsg(jsonObj);
            $('#chat-components').fadeOut();
            $('#chatWrap').fadeIn();
        }
        // else if ("close" === jsonObj.type) {
        //     refreshFriendList(jsonObj);
        // }

    };

    webSocket.onclose = function(event) {
        console.log("Disconnected!");
    };
}

//綁定點擊發送訊息事件
$('#sendMessage').on('click', function() {
    sendMessage()
});
//綁定按enter發送訊息事件
$('#message').on('keydown', function(e) {
    if (e.keyCode == 13) {
        sendMessage()
    }
});

//綁定點擊圖片觸發圖片選擇
$('#sendImg').on('click', function() {
    var friend = statusOutput.dataset.mem_id;
    if (friend == "") {
        alert("請選擇一位好友");
        return;
    }
    $('#ChatFile').click();
});

//綁定圖片change事件並發送圖片
$('#ChatFile').on('change', function(e) {
    if (this.files && this.files[0]) {
        var reader = new FileReader(); //創建檔案讀取物件
        reader.onload = function(e) {
            let myForm = new FormData(); //創建表單物件
            let imgData = e.target.result; //獲取檔案讀取後的資料
            let blob = new Blob([imgData], {
                type: 'image/*'
            }); //轉換成blob型別
            let fileName = $('#ChatFile').val(); //獲取檔案名稱
            myForm.append(fileName, blob); //把圖片加入到表單內
            $.ajax({
                type: "post",
                enctype: 'multipart/form-data',
                url: webCtx + "/websocket/websocketImgServlet",
                data: myForm,
                processData: false,
                contentType: false,
                success: function(result) {
                    let friend = statusOutput.dataset.mem_id;
                    let template = `<a href="${webCtx}/websocket/websocketImgServlet?wsImg=${result}" target="_blank"><img src="${webCtx}/websocket/websocketImgServlet?wsImg=${result}" alt=""></a>`;
                    $('#ChatFile').val(""); //清空file所選圖片
                    let jsonObj = {
                        "type": "chat",
                        "sender": self,
                        "receiver": friend,
                        "message": template,
                        "status": 1
                    };
                    webSocket.send(JSON.stringify(jsonObj));
                }
            });
        }
        reader.readAsArrayBuffer(this.files[0]);
    }
});

//ws傳送訊息function
function sendMessage() {
    var inputMessage = document.getElementById("message");
    var friend = statusOutput.dataset.mem_id;
    var message = inputMessage.value.trim();

    if (message === "") {
        alert("內容不得為空");
        inputMessage.focus();
    } else if (friend === "") {
        alert("請選擇一位好友");
    } else {
        var jsonObj = {
            "type": "chat",
            "sender": self,
            "receiver": friend,
            "message": message,
            "status": 1
        };
        webSocket.send(JSON.stringify(jsonObj));
        inputMessage.value = "";
        inputMessage.focus();
    }
}

// 更新好友列表
function refreshFriendList(jsonObj) {
    console.log(jsonObj);
    let friends;
    if (jsonObj.type == "newChat") {
        friends = JSON.parse(jsonObj.message);
        console.log(friends);
    } else {
        friends = jsonObj.users;
    }
    let row = document.getElementById("row");
    $.ajax({
        url: webCtx + "/member/memServlet",
        type: "post",
        data: {
            "action": "getMemberMap",
            "users": JSON.stringify(friends)
        },
        dataType: 'json',
        async: false,
        success: function(data) {
            row.innerHTML = '';
            for (let i = 0; i < friends.length; i++) {
                if (friends[i] == self) {
                    continue;
                }
                let tmp = `<a class="column" id="friend${friends[i]}" data-mem_id="${friends[i]}"><img src="${webCtx}/member/memImgServlet?action=headShot&mem_id=${friends[i]}">${data[friends[i]]}</a>`;
                $(row).append(tmp);
                if (jsonObj.unread != undefined && jsonObj.unread[friends[i]] != 0) { //如果未讀訊息不為0則加入通知
                    $(`#friend${friends[i]}`).append(`<span>${jsonObj.unread[friends[i]]}</span>`);
                }
            }
        }
    });
}

//好友列表更新並新增新訊息圖示
async function refreshFriendListByNewMsg(jsonObj) {
    var friends = JSON.parse(jsonObj.receiver);
    var row = document.getElementById("row");
    $(`#friend${friends}`).remove();
    let tmp = `<a class="column newMsg" id="friend${friends}" data-mem_id="${friends}"><img src="${webCtx}/member/memImgServlet?action=headShot&mem_id=${friends}">${await getMemberName(friends)}</a>`;
    $(row).prepend(tmp);
}

// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
$('#row').on('click', 'a', function(e) {
    $(e.currentTarget).addClass('active');
    $(e.currentTarget).removeClass('newMsg');
    $(e.currentTarget).siblings('a').removeClass('active');
    $(e.currentTarget).find('span').remove();
    let mem_id = e.currentTarget.dataset.mem_id;
    let friend = e.currentTarget.textContent;
    statusOutput.dataset.mem_id = mem_id;
    updateFriendName(friend);
    console.log(mem_id);
    var jsonObj = {
        "type": "history",
        "sender": self,
        "receiver": mem_id,
        "message": ""
    };
    webSocket.send(JSON.stringify(jsonObj));
})

//ws離線時function
function disconnect() {
    webSocket.close();
    document.getElementById('sendMessage').disabled = true;
}

//更新目前和誰聊天
function updateFriendName(name) {
    statusOutput.innerHTML = name;
}

//會員編號找到會員名稱
function getMemberName(mem_id) { //做一個 Promise 物件
    return new Promise(function(resolve, reject) {
        $.ajax({
            url: webCtx + "/member/memServlet",
            type: "post",
            data: {
                "action": "getMemberVO",
                "mem_id": mem_id
            },
            dataType: 'json',
            success: function(res) {
                resolve(res.mem_name); // 成功的結果用 resolve
            },
            error: function(res) {
                reject("error"); // 失敗的結果用 reject
            }
        });
    });
}


//點擊賣家頁面的我要聊聊要開始聊天
$('body').on('click', '[data-chat="active"]', async function(e) {

    //若為登入狀態在打開聊聊視窗
    if (chat_mem_id != undefined) {
        $('#chat-components').hide();
        $('#chatWrap').show();
    } else {
        alert("請登入後在進行聊聊");
        return; //若沒登入則下列不執行
    }

    let mem_id = e.currentTarget.dataset.mem_id;
    if (mem_id != chat_mem_id) {
        // let friend = await getMemberName(mem_id);
        // statusOutput.dataset.mem_id = mem_id;
        // updateFriendName(friend);
        statusOutput.innerHTML = "正在連線中...";
        $('#area').html("");
        let newChat = $(`#friend${mem_id}`).html();
        console.log(newChat);
        if (newChat == undefined) {
            let tmp = `<a class="column" id="friend${mem_id}" data-mem_id="${mem_id}"><img src="${webCtx}/member/memImgServlet?action=headShot&mem_id=${mem_id}">${await getMemberName(mem_id)}</a>`;
            $(row).append(tmp);
            $(`#friend${mem_id}`).click();
        } else {
            $(`#friend${mem_id}`).click();
        }
        // var jsonObj = {
        //     "type": "newChat",
        //     "sender": self,
        //     "receiver": mem_id,
        //     "message": ""
        // };
        // webSocket.send(JSON.stringify(jsonObj));
    } else {
        alert("跟自己聊天太邊緣了吧...");
    }
});

//點擊右下角聊聊打開聊天頁面
$('#chat-components').on('click', function(e) {
    $('#chat-components').fadeOut();
    $('#chatWrap').fadeIn();
})

//點擊縮小後關閉視窗
$('#chat-close').on('click', function(e) {
    $('#chat-components').fadeIn();
    $('#chatWrap').fadeOut();
});