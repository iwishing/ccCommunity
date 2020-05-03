$('#publishButton').click(function () {

    var title = $('#title').val().trim();
    var description = $('#description').val().trim();
    var tags = $('#tags').val().trim();

    var publishMessage = document.getElementById("registerMessage");
    var publishMessageContext = document.getElementById("registerMessageContext");
    publishMessage.style.display="none";
    var param={
        title: title,
        description: description,
        tag: tags
    }

    $.ajax({
        type: 'post',
        url: 'publishPost',
        contentType: 'application/json',
        dataType: 'json',
        data:JSOn.stringify(param),
        success(data){
            console.log(data);
            if (data == "success"){
                window.location.href="postList";
            }else{
                publishMessageContext.innerHTML=data;
                publishMessage.style.display="block";
                return false;
            }
        },
        error(err){
            console.log(err);
            return false;
        }
    })
    return false;
})