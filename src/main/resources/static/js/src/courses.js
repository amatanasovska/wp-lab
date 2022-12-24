function deleteCourse(id) {

    var row_course = document.getElementById("course"+id);
    row_course.remove();

    this.deleteProcedure(id);


}
function deleteProcedure(id) {

    fetch('/courses/delete/' + id,  {
        method: 'DELETE'
    }).then(
        function (response)
        {
            return response;
        }
    )


}
function showGrades(){
    var result_ol = document.getElementById("result");
    result_ol.innerText="";
    var from_date = document.getElementById("from").value;
    var to_date = document.getElementById("to").value;
    var from_grade = document.getElementById("from-grade").value.trim();
    var to_grade = document.getElementById("to-grade").value.trim();
    if(from_grade.length===0)
        from_grade='A';
    if(to_grade.length===0)
        to_grade='F';
    if(from_date.length===0)
    {
        from_date="all";

    }
    if(to_date.length===0)
    {
        to_date="all";
    }

    fetch('/grades/range?from=' + from_date + '&to=' + to_date + "&from_grade="+from_grade+"&to_grade=" + to_grade,  {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        },
    }).then(function (response) {
        // When the page is loaded convert it to text
        // window.location.reload();
        return response.json();

    }).then( function (result)
    {
        for (var key in result){
            var node = document.createElement('li');
            node.appendChild(document.createTextNode(result[key]['student']['username'] + " - "
                + result[key]['grade'] + " - Course : " + result[key]['course']['name']));

            result_ol.appendChild(node);
        }
    })
}
