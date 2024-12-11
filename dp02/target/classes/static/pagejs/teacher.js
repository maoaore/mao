$(function() {
    // Load teacher list when the page is ready
    loadTeacherList();
});

function loadTeacherList() {
    $.ajax({
        url: "/api/teacher/list"
    }).done(function(data) {
        let html = "";
        data.forEach((element, index) => {
            html += "<tr>";
            html += "<td>" + element.id + "</td>";
            html += "<td>" + element.name + "</td>";
            html += "<td>" + element.sex + "</td>";
            html += "<td>" + element.age + "</td>";
            html += "<td>" + element.tno + "</td>";

            html += "<td> <a href='#' onclick='showTeacherDlg(" + element.id + ")'>编辑</a> &nbsp;&nbsp;<a href='#' onclick='deleteById(" + element.id + ")'>删除</a></td>";
            html += "</tr>";
        });
        $("#teacherTb").html(html);
    });
}

let layerIndex;

/**
 * 弹出教师新增/更新对话框
 */
function showTeacherDlg(id) {
    let title = "新增教师";
    if (id) {
        // 编辑教师
        title = "编辑教师";
        $("#formId").css("display", "block");

        // 读取教师信息并赋值
        $.ajax({
            url: "/api/teacher/" + id,
            method: "GET"
        }).done(result => {
            console.log(result);

            // 遍历result对象并将值填充到 #teacherForm 表单中
            $.each(result, function(key, value) {
                // 修改选择器，确保选择的是 #teacherForm 内的字段
                var field = $('#teacherForm [name="' + key + '"]');

                if (field.is('radio')) {
                    field.filter('[value="' + value + '"]').prop('checked', true); // 选中对应的单选按钮
                } else if (field.is('checkbox')) {
                    field.prop('checked', value === "yes"); // 选中复选框
                } else {
                    field.val(value); // 填充文本框或其他字段
                }
            });
        });
    } else {
        // 新增教师
        $("#teacherForm")[0].reset();
        $("#formId").css("display", "none");
    }

    layerIndex = layer.open({
        type: 1,
        title: title,
        area: ['520px', 'auto'],
        content: $('#teacherForm')
    });
}

layui.use(function() {
    // 验证表单是否合法
    layui.form.on('submit(teacher-dlg)', function(data) {
        event.preventDefault();
        commitTeacherDlg();
    });
});

function commitTeacherDlg() {
    let id = $("#id").val();
    let formData = $("#teacherForm").serialize();
    if (id != null && id != "") {
        // 更新教师信息
        $.ajax({
            url: "/api/teacher/update",
            method: "PUT",
            data: formData
        }).done(result => {
            console.log(result);
            if (result.id) {
                loadTeacherList(); // Refresh teacher list
                console.log("update success!");
                if (layerIndex)
                    layer.close(layerIndex);
            }
        }).fail((jqXHR, textStatus, errorThrown) => {
            console.error("Request failed: " + textStatus + '-' + errorThrown);
            alert("An error occurred. Please try again.");
        });
    } else {
        // 新增教师
        $.ajax({
            url: "/api/teacher/add",
            method: "POST",
            data: formData
        }).done(result => {
            console.log(result);
            if (result.id) {
                loadTeacherList(); // Refresh teacher list
                console.log("add success!");
                if (layerIndex)
                    layer.close(layerIndex);
            }
        }).fail((jqXHR, textStatus, errorThrown) => {
            console.error("Request failed: " + textStatus + '-' + errorThrown);
            alert("An error occurred. Please try again.");
        });
    }

    $('#btnOK').prop("disabled", true).addClass("layui-btn-disabled");
}

function deleteById(id) {
    // 删除
    layer.confirm('你真的要删除吗？一旦删除不可恢复！', { icon: 3 }, function() {
        $.ajax({
            url: "/api/teacher/delete/" + id,
            method: "DELETE"
        }).done(result => {
            loadTeacherList();
        });
        layer.closeAll();
    }, function() {

    });
}