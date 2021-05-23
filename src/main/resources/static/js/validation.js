function validateForm() {
    var mail = document.forms["formR"]["email"].value;
    var pass = document.forms["formR"]["password"].value;
    if (mail == "" || pass == "") {
        alert("Campos têm de estar preenchidos");
        return false;
    }

    if(!checkP(pass)) {
        alert("A palavra pass é inválida");
        return false;
    }
}

function checkP(input) {

    var lowerCaseLetters = /[a-z]/g;
    if(!input.value.match(lowerCaseLetters)) {
        return false;
    }

    var upperCaseLetters = /[A-Z]/g;
    if(!input.value.match(upperCaseLetters)) {
        return false;
    }

    // Validate numbers
    var numbers = /[0-9]/g;
    if(!input.value.match(numbers)) {
        return false;
    }

    if(!input.value.length >= 8) {
        return false;
    }

    return true;
}