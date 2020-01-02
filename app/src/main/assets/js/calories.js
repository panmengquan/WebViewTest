function setCharts(dataLabels){
    var data = JSON.parse(dataLabels);
    $('#container').highcharts({
        chart: {
            type: 'column'
        },
        credits: {
            enabled: false,
            text:'',
        },
        title: {
            text: '',
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            min: 0,
            labels: {
                format: '{value}',
            },
            title: {
                text: '',
            },
            maxPadding:0.15,
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.2f}'
                }
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b><br/>'
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: "卡路里",
            colorByPoint: true,
            data: data
        }]
    });
}
