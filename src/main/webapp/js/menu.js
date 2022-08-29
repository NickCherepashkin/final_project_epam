
/* Когда пользователь нажимает на кнопку, переключаться раскрывает содержимое */
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}
    // Закрыть раскрывающийся список, если пользователь щелкнет за его пределами.
window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

function editDataFunction() {
    document.getElementById("name").removeAttribute("readonly");
    document.getElementById("address").removeAttribute("readonly");
    document.getElementById("contact").removeAttribute("readonly");
    document.getElementById("email").removeAttribute("readonly");

    document.getElementById("editInfo").setAttribute("type", "hidden");
    document.getElementById("back").setAttribute("type", "hidden");
    document.getElementById("changePass").setAttribute("type", "button");
    document.getElementById("saveInfo").setAttribute("type", "submit");
    document.getElementById("cancelInfo").setAttribute("type", "button");
    document.getElementById("pass").removeAttribute("password");
    document.getElementById("re_pass").removeAttribute("re_pass");
}

function changePasswordValue() {
    document.getElementById("changePass").setAttribute("type", "hidden");
    document.getElementById("pass").setAttribute("type", "password");
    document.getElementById("re_pass").setAttribute("type", "password");
    document.getElementById("pass").setAttribute("password", "");
    document.getElementById("re_pass").setAttribute("re_pass", "");
}

function cancelEditFunction() {
    document.getElementById("name").setAttribute("readonly", "readonly");
    document.getElementById("address").setAttribute("readonly", "readonly");
    document.getElementById("contact").setAttribute("readonly", "readonly");
    document.getElementById("email").setAttribute("readonly", "readonly");
    document.getElementById("pass").type = "hidden";
    document.getElementById("re_pass").type = "hidden";
    document.getElementById("pass").removeAttribute("password");
    document.getElementById("re_pass").removeAttribute("re_pass");

    document.getElementById("editInfo").type = "button";
    document.getElementById("back").type = "button";
    document.getElementById("changePass").setAttribute("type", "hidden");
    document.getElementById("saveInfo").setAttribute("type", "hidden");
    document.getElementById("cancelInfo").setAttribute("type", "hidden");
}

