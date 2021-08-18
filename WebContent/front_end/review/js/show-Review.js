//頁面載入即顯示右側的熱門文章
hotPost();

//獲取熱門文章
function hotPost() {
    $.ajax({
        type: "post",
        url: "../../post/postActionServlet",
        data: {
            "action": "hotPost",
        },
        dataType: "json",
        success: function (response) {
            if (response.length < 5) {
                for (let key = 0; key < response.length; key++) {
                    let tmp = `<a href="show-article-message.html?post_id=${response[key].MES_POST_ID}"><li>${response[key].CAT_NAME}<span>//</span>${response[key].POST_TITLE}</li></a>`;
                    $("#hot-articles").append(tmp);
                }
            } else {
                for (let key = 0; key < 6; key++) {
                    let tmp = `<a href="show-article-message.html?post_id=${response[key].MES_POST_ID}"><li>${response[key].CAT_NAME}<span>//</span>${response[key].POST_TITLE}</li></a>`;
                    $("#hot-articles").append(tmp);
                }
            }
        }
    });
}

// 獲取文章分類(只有"所有話題"不是用動態抓取的)
$.ajax({
    type: "post",
    url: "../../category/categoryServlet",
    dataType: 'json',
    success: function (result) {
        // 點選所有話題一定會到第一頁
        let tem = `<li><a href="forumindex.html?currentPage=1&rows=5">所有話題<span id="AllCatSum"></span></a></li>`;
        $("#category-data").append(tem);
        for (let i = 0; i < result.length; i++) {
            let element = result[i].cat_name;
            let template = `  
            <li><a href="forumindex.html?cat_id=${result[i].cat_id}">${element}</a><span id="thisCatSum"></span></li>
            `;
            $("#category-data").append(template);
        }
        // let tem2 = `<li><a href="">搜尋</a></li>`;
        // $("#category-data").append(tem2);
    }
});



let urlParams = new URLSearchParams(window.location.search);
let param = urlParams.get("cat_id");
let currentPage = urlParams.get("currentPage");
// rows=每頁要顯示幾篇文章
let rows = urlParams.get("rows");
console.log(currentPage);

//透過分類去秀文章
if (param != null) {
    $.ajax({
        type: "post",
        url: "../../post/showPostByCatIdServlet",
        dataType: 'json',
        data: {
            "cat_id": param
        },
        success: function (result) {
            console.log(result);
            for (let i = 0; i < result.length; i++) {
                let template = ` <div class="postone">
            <a href="show-article-message.html?post_id=${result[i].POST_ID}">
                <div class="postone_top">
                    <div class="left_author_pic">
                        <img src="../../post/postImgServlet?action=getMemHeadShot&mem_id=${result[i].MEM_ID}" width="50">
                    </div>

                    <div class="right_info">
                        <ul>
                            <li class="mem_author">${result[i].MEM_NAME}</li>
                            <li class="time">${result[i].POST_TIME}</li>
                        </ul>
                    </div>
                </div>

                <div class="postone_content">
                    <span class="postone_cat">${result[i].CAT_NAME}</span>
                    <span>// </span>

                    <span class="postone_title">${result[i].POST_TITLE}</span>
                    <div class="inner">
                        <div class="inner_text">
                            ${result[i].POST_CONTENT}
                        </div>
                    </div>
                    <div class="inner_count" id="inner_count">
                        ‧ 回覆 <span> ${getCount(result[i].POST_ID)} </span>
                    </div>
                </div>
            </a>
            <hr class="left1">
        </div>`;
                $("#post-container").append(template);
            }
            // $("#thisCatSum").text("(" + (result.length) + ")");
        }
    });

} else {
    //用頁數去找文章
    rows = 5; //限定每頁5篇文章 可自行修改
    //當已經在第一頁的情況
    if (currentPage == null || currentPage < 1) {
        currentPage = 1
    }
    $.ajax({
        type: "post",
        url: "../../post/findByPageServlet",
        dataType: 'json',
        data: {
            "currentPage": currentPage,
            "rows": rows
        },
        success: function (result) {
            console.log(result);
            for (let i = 0; i < result.list.length; i++) {
                let template = ` <div class="postone">
                <a href="show-article-message.html?post_id=${result.list[i].POST_ID}">
                    <div class="postone_top">
                        <div class="left_author_pic">
                          <img src="../../post/postImgServlet?action=getMemHeadShot&mem_id=${result.list[i].MEM_ID}" width="50">
                        </div>

                        <div class="right_info">
                            <ul>
                                <li class="mem_author">${result.list[i].MEM_NAME}</li>
                                <li class="time">${result.list[i].POST_TIME}</li>
                            </ul>
                        </div>
                    </div>

                    <div class="postone_content">
                        <span class="postone_cat">${result.list[i].CAT_NAME}</span>
                        <span>// </span>

                        <span class="postone_title">${result.list[i].POST_TITLE}</span>
                        <div class="inner">
                            <div class="inner_text">
                                ${result.list[i].POST_CONTENT}
                            </div>
                        </div>
                        <div class="inner_count">
                            ‧ 回覆 <span> ${getCount(result.list[i].POST_ID)} </span>
                        </div>
                    </div>
                </a>
                <hr class="left1">
            </div>`;
                $("#post-container").append(template);
            }

            //******頁數區******//
            //總頁數及篇數統計
            $("#totalPage").html(`第${result.currentPage}/${result.totalPage}頁       共${result.totalCount}篇文章`);

            //上一頁字樣(如果是在第一頁，就不會出現)
            if (result.currentPage == 1) {
                currentPage = 1
            } else {
                let temp1 = `<a href = "forumindex.html?currentPage=${(result.currentPage) - 1}&rows=5" >«上一頁   </a > `
                $("#page").append(temp1);
            }
            //產出動態頁數的地方 
            //#activePage是動態抓讀者點到哪一頁，再動態將active css加進來 見下方activePage function 讓被點到的頁數出現粉紅底
            for (let i = 1; i <= result.totalPage; i++) {
                let temp2 = ` <a id="activePage${i}" href = "forumindex.html?currentPage=${i}&rows=5" > ${i}</a > `;
                $("#page").append(temp2);
            }

            //下一頁字樣(如果是在最末頁，就不會出現)
            if (result.currentPage == result.totalPage) {
                return
            }
            let temp3 = `<a  href = "forumindex.html?currentPage=${(result.currentPage) + 1}&rows=5" >   下一頁»</a > `;
            $("#page").append(temp3);
        }
    });
}

// 在每則文章下方秀出他的回應總數
function getCount(post_id) {
    let count = 0;
    $.ajax({
        type: "post",
        url: "../../message/showMessageServlet",
        dataType: 'json',
        async: false,
        data: {
            "mes_post_id": post_id
        },
        success: function (result) {
            count = result.length;
        }
    });
    return count;
}

//動態抓讀者點到的頁數 把active css加入
//window.onload是確保所有東西都載入後再執行這個function
window.onload = function activePage() {
    $("#activePage" + currentPage).addClass("active");
}







