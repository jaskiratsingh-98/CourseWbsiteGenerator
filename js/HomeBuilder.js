/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function buildHome(){
    var dataFile = "./js/HomeData.json";
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
    var link1 = data.link;
    var name = data.instructor;
    var ban = $("#banner");
    ban.append(text);
    var link = $("#instructor_link");
    link.replaceWith("<a href=" + link1 + ">" + name + "</a></span>");
    var text1 = subject + number;
    var inline = $("#inlined_course");
    inline.replaceWith(text1);
    document.title = 'Home';
}
