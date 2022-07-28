
$(document).ready(function () {
    $.post({
        url: 'admin',
        data: {
            time: "1",
            choice: "newOrder"
        },
        success: function (response) {
            console.log(response);
            const myArr = JSON.parse(response);
            const label = [];
            label.push('submitted');
            label.push('success');
            label.push('canceled');
            createChart('pie', label, myArr, getColorArray(3), 'myChart', 'New order');
            document.getElementById('submitted-order').innerHTML = myArr[0];
            document.getElementById('success-order').innerHTML = myArr[1];
            document.getElementById('cancel-order').innerHTML = myArr[2];
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
    $.post({
        url: 'admin',
        data: {
            time: "1",
            choice: "revenue"
        },
        success: function (response) {
            const myArr = JSON.parse(response);
            var item = document.querySelectorAll('#revenue-body tr #revenue-statistic');
            for (var i = 0; i < myArr.length; i++) {
                item[i].innerHTML = myArr[i] + 'VND';
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
    $.post({
        url: 'admin',
        data: {
            time: "1",
            choice: "feedback"
        },
        success: function (response) {
            const myArr = JSON.parse(response);
            var item = document.querySelectorAll('#progress-bar');
            for (var i = 0; i < myArr.length; i++) {
                item[i].innerHTML = myArr[i];
                item[i].setAttribute('aria-valuenow', myArr[i]);
                item[i].setAttribute('style', 'width: ' + (100 * myArr[i] / 5) + "%;");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
    $.post({
        url: 'admin',
        data: {
            time: "1",
            choice: "customer"
        },
        success: function (response) {
            const myObj = JSON.parse(response);
            createChart2('bar', myObj.label, myObj.newlyRegister, myObj.newlyBought, 'myChart2', 'Customer', 'Newly Register', 'Newly Bought');
            var tbody = document.getElementById('customer-table-body');
            var content = "";
            for (var i = myObj.label.length - 1; i >= 0; i--) {
                content += '<tr><td>' + myObj.label[i] + '</td><td>' + myObj.newlyRegister[i] + '</td><td>' + myObj.newlyBought[i] + '</td></tr>';
            }
            tbody.innerHTML = content;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
    $.post({
        url: 'admin',
        data: {
            time: "1",
            choice: "order"
        },
        success: function (response) {
            const myObj = JSON.parse(response);
            createChart3('line', myObj.label, myObj.successOrder, myObj.allOrder, 'myChart3', 'Orders Count', 'Success Orders', 'All Orders');
            var tbody = document.getElementById('order-table-body');
            var content = "";
            for (var i = myObj.label.length - 1; i >= 0; i--) {
                content += '<tr><td>'+myObj.label[i]+'</td><td>'+myObj.successOrder[i]+'</td><td>'+myObj.allOrder[i]+'</td></tr>';
            }
            tbody.innerHTML = content;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
});

function getColorCode() {
    var makeColorCode = '0123456789ABCDEF';
    var code = '#';
    for (var count = 0; count < 6; count++) {
        code = code + makeColorCode[Math.floor(Math.random() * 16)];
    }
    return code;
}
function getColorArray(number) {
    var arr = "";
    for (var i = 0; i < number; i++) {
        arr += getColorCode() + ";";
    }
    arr = arr.substring(0, arr.length - 1);
    return arr.split(";");
}
var myChart1;
var myChart2;
var myChart3;
function createChart(type, label = [], data = [], color = [], id, title) {
    const ctx = document.getElementById(id).getContext('2d');
    myChart1 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Revenues',
                    data: data,
                    backgroundColor: color,
                    borderWidth: 1
                }]
        },
        options: {
            legend: {
                display: false,
                position: 'left'
            },
            title: {
                display: true,
                text: title,
                position: 'top',
                fontSize: 16,
                padding: 20
            },
            scales: {
                yAxes: [{
                        ticks: {
                            min: 0
                        }
                    }]
            },
            options: {
                responsive: true,
                datalabels: {// This code is used to display data values
                    anchor: 'end',
                    align: 'top',
                    formatter: Math.round,
                    font: {
                        weight: 'bold',
                        size: 16
                    }
                },
                plugins: {
                    title: {
                        display: true,
                        text: 'Custom Chart Title'
                    }
                }
            }
        }
    });
}
function updateChart(type, label = [], data = [], color = [], id, title) {
    myChart1.destroy();
    const ctx = document.getElementById(id).getContext('2d');
    myChart1 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Revenues',
                    data: data,
                    backgroundColor: color,
                    borderWidth: 1
                }]
        },
        options: {
            legend: {
                display: false,
                position: 'left'
            },
            title: {
                display: true,
                text: title,
                position: 'top',
                fontSize: 16,
                padding: 20
            },
            scales: {
                yAxes: [{
                        ticks: {
                            min: 0
                        }
                    }]
            },
            options: {
                responsive: true,
                datalabels: {// This code is used to display data values
                    anchor: 'end',
                    align: 'top',
                    formatter: Math.round,
                    font: {
                        weight: 'bold',
                        size: 16
                    }
                },
                plugins: {
                    title: {
                        display: true,
                        text: 'Custom Chart Title'
                    }
                }
            }
        }
    });
}
function createChart2(type, label = [], data1 = [], data2 = [], id, title, label1, label2) {
    const ctx = document.getElementById(id).getContext('2d');
    myChart2 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: label1,
                    data: data1,
                    backgroundColor: 'pink',
                    borderWidth: 1
                },
                {
                    label: label2,
                    data: data2,
                    backgroundColor: 'yellow',
                    borderWidth: 1
                }]
        },
        options: {
            legend: {
                display: false,
                position: 'left'
            },
            title: {
                display: true,
                text: title,
                position: 'top',
                fontSize: 16,
                padding: 20
            },
            scales: {
                yAxes: [{
                        ticks: {
                            min: 0
                        }
                    }]
            },
            options: {
                responsive: true,
                datalabels: {// This code is used to display data values
                    anchor: 'end',
                    align: 'top',
                    formatter: Math.round,
                    font: {
                        weight: 'bold',
                        size: 16
                    }
                },
                plugins: {
                    title: {
                        display: true,
                        text: 'Custom Chart Title'
                    }
                }
            }
        }
    });
}
function updateChart2(type, label = [], data1 = [], data2 = [], id, title, label1, label2) {
    myChart2.destroy();
    const ctx = document.getElementById(id).getContext('2d');
    myChart2 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: label1,
                    data: data1,
                    backgroundColor: 'pink',
                    borderWidth: 1
                },
                {
                    label: label2,
                    data: data2,
                    backgroundColor: 'yellow',
                    borderWidth: 1
                }]
        },
        options: {
            legend: {
                display: false,
                position: 'left'
            },
            title: {
                display: true,
                text: title,
                position: 'top',
                fontSize: 16,
                padding: 20
            },
            scales: {
                yAxes: [{
                        ticks: {
                            min: 0
                        }
                    }]
            },
            options: {
                responsive: true,
                datalabels: {// This code is used to display data values
                    anchor: 'end',
                    align: 'top',
                    formatter: Math.round,
                    font: {
                        weight: 'bold',
                        size: 16
                    }
                },
                plugins: {
                    title: {
                        display: true,
                        text: 'Custom Chart Title'
                    }
                }
            }
        }
    });
}
function createChart3(type, label = [], data1 = [], data2 = [], id, title, label1, label2) {
    const ctx = document.getElementById(id).getContext('2d');
    myChart3 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: label1,
                    data: data1,
                    fill: false,
                    borderColor: 'red',
                    tension: 0.1,
                    borderWidth: 1,
                    pointRadius: 0
                },
                {
                    label: label2,
                    data: data2,
                    fill: false,
                    borderColor: 'yellow',
                    tension: 0.1,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: true,
                position: 'right'
            },
            title: {
                display: true,
                text: title,
                position: 'top',
                fontSize: 16,
                padding: 20
            },
            scales: {
                yAxes: [{
                        ticks: {
                            min: 0
                        }
                    }]
            },
            options: {
                responsive: true,
                datalabels: {// This code is used to display data values
                    anchor: 'end',
                    align: 'top',
                    formatter: Math.round,
                    font: {
                        weight: 'bold',
                        size: 16
                    }
                },
                plugins: {
                    title: {
                        display: true,
                        text: 'Custom Chart Title'
                    }
                }
            }
        }
    });
}
function updateChart3(type, label = [], data1 = [], data2 = [], id, title, label1, label2) {
    myChart3.destroy();
    const ctx = document.getElementById(id).getContext('2d');
    myChart3 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: label1,
                    data: data1,
                    fill: false,
                    borderColor: 'red',
                    tension: 0.1,
                    borderWidth: 1,
                    pointRadius: 0
                },
                {
                    label: label2,
                    data: data2,
                    fill: false,
                    borderColor: 'yellow',
                    tension: 0.1,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: true,
                position: 'right'
            },
            title: {
                display: true,
                text: title,
                position: 'top',
                fontSize: 16,
                padding: 20
            },
            scales: {
                yAxes: [{
                        ticks: {
                            min: 0
                        }
                    }]
            },
            options: {
                responsive: true,
                datalabels: {// This code is used to display data values
                    anchor: 'end',
                    align: 'top',
                    formatter: Math.round,
                    font: {
                        weight: 'bold',
                        size: 16
                    }
                },
                plugins: {
                    title: {
                        display: true,
                        text: 'Custom Chart Title'
                    }
                }
            }
        }
    });
}
function checkFirstDateInput(item) {

    var second = document.getElementById('end-date');

    var startDate = new Date(item.value);
    var endDate = new Date(second.value);
    if (startDate.getTime() > endDate.getTime()) {
        item.value = second.value;
        alert("Start date can not be greater than end date");
    }
}

function checkEndDateInput(item) {

    var first = document.getElementById('start-date');

    var now = new Date();
    var startDate = new Date(first.value);
    var endDate = new Date(item.value);
    if (startDate.getTime() > endDate.getTime()) {
        item.value = first.value;
        alert("End date can not be less than start date");
    }
    ;
    var today = "";
    var year = now.getFullYear();
    today += year + "-";
    var month = now.getMonth() + 1;
    if (month < 10) {
        today += "0" + month + "-";
    } else {
        today += month + "-";
    }
    var date = now.getDate();
    if (date < 10) {
        today += "0" + date;
    } else {
        today += date;
    }

    if (endDate.getTime() > now.getTime()) {
        alert("End date can not greater than now");
        item.value = today;
    }
}

function changeSale(sale) {
    document.getElementById('orderTrendSaleId').value = sale.value;
}
function changeTime(time) {
    document.getElementById('orderTrendTime').value = time.value;
}
function submitFrm() {
    document.getElementById('frm').submit();
}

function newOrderStatistic(item) {
    $.post({
        url: "admin",
        data: {
            time: item.value,
            choice: "newOrder"
        },
        success: function (response) {
            const myArr = JSON.parse(response);
            const label = ['submitted', 'success', 'canceled'];
            updateChart('pie', label, myArr, getColorArray(3), 'myChart', 'New order');
            document.getElementById('submitted-order').innerHTML = myArr[0];
            document.getElementById('success-order').innerHTML = myArr[1];
            document.getElementById('cancel-order').innerHTML = myArr[2];
        }
    });
}
function newRevenueTime(item) {
    $.post({
        url: 'admin',
        data: {
            time: item.value,
            choice: "revenue"
        },
        success: function (response) {
            console.log(response);
            const myArr = JSON.parse(response);
            var item = document.querySelectorAll('#revenue-body tr #revenue-statistic');
            for (var i = 0; i < myArr.length; i++) {
                item[i].innerHTML = myArr[i] + 'VND';
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
function newFeedbackTime(item) {
    $.post({
        url: 'admin',
        data: {
            time: item.value,
            choice: "feedback"
        },
        success: function (response) {
            const myArr = JSON.parse(response);
            var item = document.querySelectorAll('#progress-bar');
            for (var i = 0; i < myArr.length; i++) {
                item[i].innerHTML = myArr[i];
                item[i].setAttribute('aria-valuenow', myArr[i]);
                item[i].setAttribute('style', 'width: ' + (100 * myArr[i] / 5) + "%;");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
function newCustomerTime(item) {
    $.post({
        url: 'admin',
        data: {
            time: item.value,
            choice: "customer"
        },
        success: function (response) {
            const myObj = JSON.parse(response);
            console.log(myObj.newlyBought);
            console.log(myObj.label);
            console.log(myObj.newlyRegister);
            updateChart2('bar', myObj.label, myObj.newlyRegister, myObj.newlyBought, 'myChart2', 'Customer');
            var tbody = document.getElementById('customer-table-body');
            var content = "";
            for (var i = myObj.label.length - 1; i >= 0; i--) {
                content += '<tr><td>' + myObj.label[i] + '</td><td>' + myObj.newlyRegister[i] + '</td><td>' + myObj.newlyBought[i] + '</td></tr>';
            }
            tbody.innerHTML = content;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
function newOrderTime(item) {
    $.post({
        url: 'admin',
        data: {
            time: item.value,
            choice: "order"
        },
        success: function (response) {
            const myObj = JSON.parse(response);
            updateChart3('line', myObj.label, myObj.successOrder, myObj.allOrder, 'myChart3', 'Orders Count', 'Success Orders', 'All Orders');
            var tbody = document.getElementById('order-table-body');
            var content = "";
            for (var i = myObj.label.length - 1; i >= 0; i--) {
                content += '<tr><td>'+myObj.label[i]+'</td><td>'+myObj.successOrder[i]+'</td><td>'+myObj.allOrder[i]+'</td></tr>';
            }
            tbody.innerHTML = content;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}
function submitFormWithNewOrderOption(item) {
    document.getElementById('orderOption').value = item.value;
    document.getElementById('submit-frm').submit();
}
function submitFormWithSequence(item) {
    document.getElementById('sequence').value = item.value;
    document.getElementById('submit-frm').submit();
}
function submitFormWithNewPage(page) {
    document.getElementById('pageNumber').value = page;
    document.getElementById('submit-frm').submit();
}
