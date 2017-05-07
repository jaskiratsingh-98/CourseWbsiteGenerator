/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function buildHome(){
    var datfile = "./js/CourseInfo.json";
    loadData(dataFile, loadCourseInfo);
}

function loadData(jsonFile, callback) {
    $.getJSON(jsonFile, function(json) {
        callback(json);
    });
}

function loadCourseInfo(json){
    setCourseInfo(json);
}

function setCourseInfo(data){
    var subject = data.subject;
    var number = data.number;
    var semester = data.semester;
    var year = data.year;
    var title = data.title;
    var text = subject + " " + number + " - " + semester + " " + year
    + "<br>" + title;
    var link1 = data.ins_link;
    var name = data.name;
    var ban = $("#banner");
    ban.append(text);
    var link = $("#instructor_link");
    link.replaceWith("<a href=" + link1 + ">" + name + "</a></span>");
    document.title = 'Home';
}
