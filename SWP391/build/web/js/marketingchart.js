$(document).ready(function () {
    $.post({
        url: 'dashboard',
        data: {
            time: '1',
            choice: 'newCustomer'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            createChart('bar', myArr.label, myArr.data, getColorArray(7).split(";"), 'myChart', 'New Customers Trend');
            var tbody = document.getElementById('customer-table-body');
            var content = "";
            for (var i = myArr.label.length - 1; i >= 0; i--) {
                content += '<tr><td>' + myArr.label[i] + '</td><td>' + myArr.data[i] + '</td></tr>';
            }
            tbody.innerHTML = content;
        }
    });
    $.post({
        url: 'dashboard',
        data: {
            time: '1',
            choice: 'post'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            createChart2('line', myArr.label, myArr.data, 'green', 'postChart', 'Posts');
            document.getElementById('new-post').innerHTML = myArr.new;
        }
    });
    $.post({
        url: 'dashboard',
        data: {
            time: '1',
            choice: 'product'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            document.getElementById('new-product').innerHTML = myArr.new;
            createChart3('line', myArr.label, myArr.data, 'red', 'productChart', 'Product');
        }
    });
    $.post({
        url: 'dashboard',
        data: {
            time: '1',
            choice: 'customer'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            document.getElementById('new-customer').innerHTML = myArr.new;
            createChart4('line', myArr.label, myArr.data, 'yellow', 'customerChart', 'Customer');
        }
    });
    $.post({
        url: 'dashboard',
        data: {
            time: '1',
            choice: 'feedback'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            document.getElementById('new-feedback').innerHTML = myArr.new;
            createChart5('line', myArr.label, myArr.data, 'lightblue', 'feedbackChart', 'Feedbacks');
        }
    });
});

document.querySelector('#post-total').addEventListener('click', function () {
    location.hash = "post-statistic";
});
document.querySelector('#product-total').addEventListener('click', function () {
    location.hash = "product-statistic";
});
document.querySelector('#customer-total').addEventListener('click', function () {
    location.hash = "customer-statistic";
    location.href = location.href.split("#")[0];
});
document.querySelector('#feedback-total').addEventListener('click', function () {
    location.hash = "feedback-statistic";
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
    return arr;
}
var myChart1;
var myChart2;
var myChart3;
var myChart4;
var myChart5;
function createChart2(type, label = [], data = [], color, id, title) {
    const ctx = document.getElementById(id).getContext('2d');
    myChart2 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Post',
                    data: data,
                    fill: false,
                    borderColor: color,
                    tension: 0.4,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: false,
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
function updateChart2(type, label = [], data = [], color, id, title) {
    myChart2.destroy();
    const ctx = document.getElementById(id).getContext('2d');
    myChart2 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Post',
                    data: data,
                    fill: false,
                    borderColor: color,
                    tension: 0.4,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: false,
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
function createChart3(type, label = [], data = [], color, id, title) {
    const ctx = document.getElementById(id).getContext('2d');
    myChart3 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Products',
                    data: data,
                    fill: false,
                    borderColor: color,
                    tension: 0.4,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: false,
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
function updateChart3(type, label = [], data = [], color, id, title) {
    myChart3.destroy();
    const ctx = document.getElementById(id).getContext('2d');
    myChart3 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Post',
                    data: data,
                    fill: false,
                    borderColor: color,
                    tension: 0.1,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: false,
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
function createChart4(type, label = [], data = [], color, id, title) {
    const ctx = document.getElementById(id).getContext('2d');
    myChart4 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Customers',
                    data: data,
                    fill: false,
                    borderColor: color,
                    tension: 0.1,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: false,
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
function updateChart4(type, label = [], data = [], color, id, title) {
    myChart4.destroy();
    const ctx = document.getElementById(id).getContext('2d');
    myChart4 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Customer',
                    data: data,
                    fill: false,
                    borderColor: color,
                    tension: 0.1,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: false,
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
function createChart5(type, label = [], data = [], color, id, title) {
    const ctx = document.getElementById(id).getContext('2d');
    myChart5 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Feedbacks',
                    data: data,
                    fill: false,
                    borderColor: color,
                    tension: 0.1,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: false,
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
function updateChart5(type, label = [], data = [], color, id, title) {
    myChart5.destroy();
    const ctx = document.getElementById(id).getContext('2d');
    myChart5 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'Feedbacks',
                    data: data,
                    fill: false,
                    borderColor: color,
                    tension: 0.1,
                    borderWidth: 1,
                    pointRadius: 0
                }]
        },
        options: {
            legend: {
                display: false,
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
function createChart(type, label = [], data = [], color = [], id, title) {
    const ctx = document.getElementById(id).getContext('2d');
    myChart1 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'New Customers',
                    data: data,
                    backgroundColor: color,
                    borderWidth: 1
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
function updateChart(type, label = [], data = [], color = [], id, title) {
    myChart1.destroy();
    const ctx = document.getElementById(id).getContext('2d');
    myChart1 = new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: 'New Customers',
                    data: data,
                    backgroundColor: color,
                    borderWidth: 1
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
        document.getElementById('mess').innerHTML = 'Start date can not be greater than end date';
        document.getElementById('mess').style.visibility = 'visible';
    }
}

function checkEndDateInput(item) {

    var first = document.getElementById('start-date');

    var now = new Date();
    var startDate = new Date(first.value);
    var endDate = new Date(item.value);
    if (startDate.getTime() > endDate.getTime()) {
        item.value = first.value;
        document.getElementById('mess').innerHTML = 'End date can not be less than start date';
        document.getElementById('mess').style.visibility = 'visible';
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
        document.getElementById('mess').innerHTML = 'End date can not greater than now';
        document.getElementById('mess').style.visibility = 'visible';
        item.value = today;
    }
}
function newCustomerTrend(item) {
    $.post({
        url: 'dashboard',
        data: {
            time: item.value,
            choice: 'newCustomer'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            console.log(myArr);
            updateChart('bar', myArr.label, myArr.data, getColorArray(myArr.label.length).split(";"), 'myChart', 'New Customers Trend');
            var tbody = document.getElementById('customer-table-body');
            var content = "";
            for (var i = myArr.label.length - 1; i >= 0; i--) {
                content += '<tr><td>' + myArr.label[i] + '</td><td>' + myArr.data[i] + '</td></tr>';
            }
            tbody.innerHTML = content;
        }
    });
}
function newFeedbackTimeRange(item) {
    $.post({
        url: 'dashboard',
        data: {
            time: item.value,
            choice: 'feedback'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            document.getElementById('new-feedback').innerHTML = myArr.new;
            updateChart5('line', myArr.label, myArr.data, 'lightblue', 'feedbackChart', 'Feedbacks');
        }
    });
}
function newPostTimeRange(item) {
    $.post({
        url: 'dashboard',
        data: {
            time: item.value,
            choice: 'post'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            document.getElementById('new-post').innerHTML = myArr.new;
            updateChart2('line', myArr.label, myArr.data, 'green', 'postChart', 'Posts');
        }
    });
}
function newProductTimeRange(item) {
    $.post({
        url: 'dashboard',
        data: {
            time: item.value,
            choice: 'product'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            document.getElementById('new-product').innerHTML = myArr.new;
            updateChart3('line', myArr.label, myArr.data, 'red', 'productChart', 'Product');
        }
    });
}
function newCustomerTimeRange(item) {
    $.post({
        url: 'dashboard',
        data: {
            time: item.value,
            choice: 'customer'
        },
        success: function (response) {
            var myArr = JSON.parse(response);
            document.getElementById('new-customer').innerHTML = myArr.new;
            updateChart4('line', myArr.label, myArr.data, 'yellow', 'customerChart', 'Customer');
        }
    });
}


