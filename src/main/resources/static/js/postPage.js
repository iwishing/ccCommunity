//发表二级评论
function secondReply(e) {
    //根据自己的data-id属性获取id
    var id = e.getAttribute("data-id");
    var postId = $('#PostIdHidden').val().trim();
    var content = $('#secondReplyArea-'+id).val().trim();

    var secondReplyMessage = document.getElementById("secondReplyMessage");
    var secondReplyMessageContext = document.getElementById("secondReplyMessageContext");

    secondReplyMessage.style.display="none";
    if (content == ''){
        secondReplyMessage.style.display="block";
        secondReplyMessageContext.innerHTML="回复不能为空！";
        setTimeout(function(){repleyMessage.style.display="none";}, 2000);
        return false;
    }

    var param = {
        postId: postId,
        parent_id: id,
        type: 2,
        content: content
    }
    $.ajax({
        type: 'POST',
        url: '/comment',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(param),
        success:function (data) {
            if (data == "success"){
                window.location.reload();
                //如果评论成功，重新加载界面，且展开改评论的二级评论框
                //根据父评论id找到自己的二级评论框
                var secondComment = $('#comment-'+id);
                var collapse = $('#collapse-'+id);
                //获取该id对应的collapse框
                collapse.classList.add("active");
                secondComment.addClass("in");
                $('#commentId')
            }else{
                var isAccepted = confirm(data);
                if (isAccepted){
                    $('#loginModel').modal({show:true});
                }else {
                    secondReplyMessage.style.display="block";
                    secondReplyMessageContext.innerHTML=data;
                    setTimeout(function(){secondReplyMessage.style.display="none";}, 2000);
                }
                return false;
            }
            console.log(data);
        },
        error:function (err) {
            console.log(err);
            return false;
        }
    })
    return false;
}
$(function(){

    // 发表评论
    $('#replyButton').click(function () {
        var postId = $('#PostIdHidden').val().trim();
        var parent_id = $('#PostIdHidden').val().trim();
        var content = $('#replyContent').val().trim();

        var replyContext = document.getElementById("replyContext");
        var repleyMessage = document.getElementById("repleyMessage");
        // var repleyMessageContext = document.getElementById("repleyMessageContext");


        repleyMessage.style.display="none";
        $('#messageButton').removeClass("btn-success").addClass("btn-danger");
        if (content == ''){
            repleyMessage.style.display="block";
            repleyMessageContext.innerHTML="回复不能为空！";
            setTimeout(function(){repleyMessage.style.display="none";}, 2000);
            return false;
        }

        var param = {
            postId: postId,
            parent_id: parent_id,
            type: 1,
            content: content
        }

        $.ajax({
            type: 'post',
            url: '/comment',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(param),
            success:function (data) {
                if (data == "success"){
                    window.location.reload();
                }else{
                    var isAccepted = confirm(data);
                    if (isAccepted){
                        $('#loginModel').modal({show:true});
                    }else {
                    repleyMessage.style.display="block";
                    repleyMessageContext.innerHTML=data;
                    setTimeout(function(){repleyMessage.style.display="none";}, 2000);
                    }
                    return false;
                }
                console.log(data);
            },
            error:function (err) {
                console.log(err);
                return false;
            }
        })
        return false;
    })
})



//展开二级评论框
function collapseComments(e) {
    //获取该评论按钮的data-id属性
    var id = e.getAttribute("data-id");
    //获取该id对应的collapse框
    var comment = $('#comment-'+id);
    //获取collapse状态
    var collapseStatus = e.getAttribute("collapseStatus");

    //如果collapse存在
    if(collapseStatus){
        //代表打开了，现在关上，顺便删除状态
        e.classList.remove("active");
        comment.removeClass("in");
        e.removeAttribute("collapseStatus");
    }else{
        //打开的同时发送请求拿到子评论列表
        var subCommentContainer = $("#comment-"+id);

        //等于1的时候代表只有一个子元素，即输入框，我们就要重新拉取数据，否则代表展示过了，就不用再拉取数据，直接展开就行
        if (subCommentContainer.children().length != 1){
            //展开，并添加状态
            e.classList.add("active");
            comment.addClass("in");
            e.setAttribute("collapseStatus","in");
        }else {
            $.getJSON("/comment/"+id, function (data) {

                $.each(data.reverse(), function (index,thisComment) {

                    //1`二级评论框架
                    var secondCommentContent = $("<div/>", {
                        "class": "media seconderyReply",
                    });
                    //2`隐藏框，存放coment.id
                    var hiddenInput = $("<input/>",{
                        "id": 'commentId-'+thisComment.id,
                        "type": "hidden",
                        "value": thisComment.id
                    });
                    //3`媒体组件左部分
                    var mediaLeft = $("<div/>",{
                        "class": "media-left"
                    })
                    //4`媒体组件左部分的a标签
                    var mediaLeftLabelA = $("<a/>",{
                        "href": ""
                    })
                    //5`媒体组件左部分a标签里面的img
                    var mediaLeftLabelA_img = $("<img/>",{
                        "style": "width: 38px",
                        "class": "media-object img-circle",
                        "src": thisComment.user.avatar
                    })
                    //6`媒体主体
                    var mediaBody = $("<div/>",{
                        "class": "media-body"
                    })
                    //7`媒体主体的头
                    var mediaBodyHead = $("<a/>",{
                        "href": ""
                    })
                    //8`媒体主体的头中的h5
                    var mediaBodyHeadH5 = $("<h5/>",{
                        "class": "media-heading",
                        "text": thisComment.user.name
                    })
                    //9`媒体主体中的第一个span
                    var mediaBodySpan = $("<span/>",{
                        "text": thisComment.content
                    })
                    //10`评论主体下面的小物件
                    var mediaBodyCommentBody = $("<div/>",{
                        "class": "commentBody"
                    })
                    //11`小物件里面的左边的a标签
                    var CommentBodyLabalALeft = $("<a/>",{
                        "style": "margin-right: 50px",
                        "class": "postCommentItemIcon",
                        "href": ""
                    })
                    //12`小物件里面的左边的a标签里面的span
                    var CommentBodyLabalALeftSpan = $("<span/>",{
                        "class": "glyphicon glyphicon-heart",
                        "aria-hidden": "true"
                    })
                    //13`小物件里面的左边的a标签里面的span里面的span
                    var CommentBodyLabalALeftSpanSpan = $("<span/>",{
                        "text": thisComment.like_count,
                        "style": "font-size: 5px"
                    })
                    //14`小物件里面的右边div
                    var CommentBodyDivRight = $("<div/>",{
                        "class": "postCommentItemIcon",
                        "style": "float: right"
                    })
                    //15`小物件里面的右边div中的span
                    var CommentBodyDivRightSpan = $("<span/>",{
                        //设置时间格式
                        html: moment(thisComment.gmtCreate).format('YYYY-MM-d hh:mm')
                })


                    CommentBodyDivRight.append(CommentBodyDivRightSpan);
                    CommentBodyLabalALeftSpan.append(CommentBodyLabalALeftSpanSpan);
                    CommentBodyLabalALeft.append(CommentBodyLabalALeftSpan);
                    mediaBodyCommentBody.append(CommentBodyLabalALeft);
                    mediaBodyCommentBody.append(CommentBodyDivRight);
                    mediaBodyHead.append(mediaBodyHeadH5);
                    mediaBody.append(mediaBodyHead);
                    mediaBody.append(mediaBodySpan);
                    mediaBody.append(mediaBodyCommentBody);
                    mediaLeftLabelA.append(mediaLeftLabelA_img);
                    mediaLeft.append(mediaLeftLabelA);
                    secondCommentContent.append(hiddenInput);
                    secondCommentContent.append(mediaLeft);
                    secondCommentContent.append(mediaBody);
                    subCommentContainer.prepend(secondCommentContent);
                });
            });

            e.classList.add("active");
            comment.addClass("in");
            e.setAttribute("collapseStatus","in");
        }
    }
}
