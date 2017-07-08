var Layout = function () {
    var elements = {
        loginInput: $("#loginimg input"),
        loginBlock: $('.block'),
        loginLoged: $('#loged'),
        login: $('#login'),
        loginError: $('#check'),
        loginImageHover: $("#loginhover")
    }

    //intput holds login form display
    elements.loginInput.focus(function () {
        elements.loginBlock.css('display', 'block');
        elements.loginImageHover.css('background', 'url(images/login3.png) top left no-repeat');
        elements.loginImageHover.css('background-size', 'cover');
    })

    elements.loginInput.focusout(function () {
        elements.loginBlock.css('display', 'none');
        elements.loginImageHover.css('background', '');
    })

    function change(x) {
        if (x) { //user logat
            elements.loginLoged.css('display', 'block');
        } else {
            elements.login.css('display', 'block');
            elements.loginError.css('display', 'block');
        }
    }

    function shakediv() {
        elements.loginBlock.css('display', 'block');
        elements.loginBlock.effect("shake");
        setTimeout(function () {
            elements.loginBlock.css('display', 'none');
        }, 1700);
    }
}

var layout = new Layout();
