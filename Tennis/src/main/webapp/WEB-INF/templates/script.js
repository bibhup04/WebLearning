let btnAdd = document.querySelector('button');
let tab = document.querySelector('table');

let headingInput = document.querySelector('#heading');
let bodyInput = document.querySelector('#body');
let authorInput = document.querySelector('#author');

var table, rows, switching, i, x, y, shouldSwitch;
table = document.querySelector(".table");

btnAdd.addEventListener('click', () =>{
    let heading = headingInput.value;
    let body = bodyInput.value;
    let author = authorInput.value;
    if (heading.length === 0 || body.trim() === '' || author.length === 0 ) {
        alert("Please fill in all the fields.");
        return;
    }

    let template =`
                    <tr>
                    <td>${heading}</td><td>${body}</td><td>${author}</td>
                    </tr>`;

    table.innerHTML += template;
});

function hideAllData(){
    console.log("hey");
    var x = document.querySelector("noticeForm");
    x.style.display = !x.style.display;
}