$(function () {
    $('#publishButton').click(function () {

        var title = $('#title').val().trim();
        var description = $('#description').val().trim();
        var tag = $('#tags').val().trim();

        var publishMessage = document.getElementById("publishMessage");
        var publishMessageContext = document.getElementById("publishMessageContext");
        publishMessage.style.display = "none";

        var param = {
            title: title,
            description: description,
            tag: tag,
        }

        $.ajax({
            type: 'post',
            url: '/publishPost',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(param),
            success: function (data) {
                console.log(data);
                if (data == "success") {
                    window.location.href = "postList";
                } else {
                    publishMessageContext.innerHTML = data;
                    publishMessage.style.display = "block";
                    return false;
                }
            },
            error: function (err) {
                console.log(err);
                return false;
            }
        })
        return false;
    })
})