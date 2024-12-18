


let layerIndex;

/**
 * 弹出教师新增/更新对话框
 */
function showTeacherDlg(id) {
    let title = id ? "编辑" : "新增"; // 根据 ID 判断是编辑还是新增
    $("#formId").css("display", "block"); // 显示表单

    if (id) {
        // 编辑
        $.ajax({
            url: "/api/teacher/" + id, // 获取学生信息
            method: "GET"
        }).done(result => {
            console.log(result); // Debug: 查看返回的数据
            // 确保API返回数据结构符合预期
            if (result.code === 0 && result.data) {
                // 遍历 result.data 对象并将值填充到 #studForm 表单中
                $.each(result.data, function (key, value) {
                    var field = $('#teacherForm [name="' + key + '"]'); // 获取对应的表单元素
                    if (field.length) {
                        if (field.is(':radio')) {
                            // 单选按钮处理
                            field.filter('[value="' + (value === 1 ? '1' : '0') + '"]').prop('checked', true);
                        } else if (field.is(':checkbox')) {
                            // 复选框处理
                            field.prop('checked', value === "yes"); // 假设"yes"表示选择状态
                        } else {
                            // 填充文本框或其他输入类型
                            field.val(value);
                        }
                    }
                });
              } else {
                  alert("获取学生信息失败或学生不存在"); // 错误反馈
             }
        }).fail((jqXHR, textStatus, errorThrown) => {
            console.error("请求失败: " + textStatus + " - " + errorThrown);
            alert("获取学生信息失败，请重试");
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
function getSearchCondition(){
    let formData = {}
    //遍历每个输入的元素，将其值存储到formData中
    $('#queryForm').find('input,select').each(function(){
        let name = $(this).attr('name');
        let value = $(this).val();
        //只有name属性存在且不为空时才台南佳到formData中
        if(name && value){
            formData[name] = value;
        }
    });
    return formData
}

layui.use(function() {
    // 验证表单是否合法
    layui.form.on('submit(teacher-dlg)', function(data) {
        event.preventDefault();
        commitTeacherDlg();
    })
    //表格初始化
    const table = layui.table;
    let teacher = getSearchCondition();

    //创建渲染实例
    table.render({
        elem:'#tbTeacher',
        url:'/api/teacher/getbypage',
        method:"POST",
        contentType:'application/json',
        where:{"data":teacher},
        page:true,
        cols:[[
            {type:'checkbox',fixed:'left'},
            {field:'id',fixed:'left',width:80,title:'id',sort:true},
            {field:'name',title:'姓名'},
            {
                field:'tno',
                title:'学号',
                width:150,
            },
            {field:'sex',width:80,title:'性别',sort:true,
                templet:d => d.sex === 1 ? '男' : (d.sex === 2 ? '女' : '未知'),
            },
            {field:'age',width:100,title:'年龄',sort:true},
            {field:'right',width:134,title:'操作',minWidth:125,templet:'#editTemplate'}
        ]],
        done: function(rs){
            //console.log(rs)
        }
    });

    table.on('tool(tbTeacher)', function(obj){
        var data = obj.data;//获取当前行数据
        if (obj.event === 'edit'){
        showTeacherDlg(data.id);
        }
    });
});
function search(){
    let teacher = getSearchCondition();
    const table = layui.table;
    table.reloadData("tbTeacher",{
        where:{data:teacher}
    });
    console.log("where condition:"+JSON.stringify(teacher))
}
function deleteConfirm(){
    const table = layui.table;
    //获取表格选中状态
    const checkStatus = table.checkStatus('tbTeacher');
    const selectedData = checkStatus.data; // 获取选中项的数据
    // 如果没有选择任何学生，提示用户
    if (selectedData.length === 0) {
        layer.alert("请先选择要删除的学生!");
        return;
    }

    // 提取选中学生的ID
    const idsToDelete = selectedData.map(item => item.id);

    // 弹出确认框，确认删除操作
    layer.confirm('您确定要删除选中的学生吗？此操作无法恢复！', { icon: 3, title: '警告' }, function (index) {
        $.ajax({
            url: "/api/teacher/delete", // 确保URL正确
            method: "DELETE", // 设置请求方法为DELETE
            contentType: "application/json", // 指定内容类型为JSON
            data: JSON.stringify(idsToDelete), // 把ID数组转换为JSON字符串并传递
            success: function (result) {
                if (result.code === 0) {
                    layer.msg("删除成功!"); // 显示删除成功的消息
                    table.reload('tbStudent'); // 直接使用 table.reload 刷新表格
                } else {
                    // 显示错误消息
                    layer.alert("删除失败: " + result.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // 打印错误信息到控制台，便于调试
                console.error("请求失败: ", jqXHR);
                layer.alert("请求失败，请稍后重试. 错误: " + errorThrown); // 提示用户请求失败
            }
        });

        layer.close(index); // 关闭确认框
    }, function () {
        layer.msg('已取消删除操作'); // 用户取消删除时显示的消息
    });


}
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

// function deleteById(id) {
//     // 删除
//     layer.confirm('你真的要删除吗？一旦删除不可恢复！', { icon: 3 }, function() {
//         $.ajax({
//             url: "/api/teacher/delete/" + id,
//             method: "DELETE"
//         }).done(result => {
//             loadTeacherList();
//         });
//         layer.closeAll();
//     }, function() {
//
//     });
// }