define(['angular'], function(angular){
    angular.module('add-order-controller', []).controller('addOrderController', function($scope, $routeParams, $navigate, $ajax, $rootScope){
        $scope.data = {
            arriveTime: 0
        };

        $scope.init = function() {
            $(".select").mobiscroll().select({
                lang: "zh",
                theme: "mobiscroll",
                display: "bottom"
            });
            var type = $routeParams.type || 1;
            $(".problemCategory").val(type);

            $(".datetimepicker").mobiscroll().date({
                lang: "zh",
                theme: "mobiscroll",
                display: "bottom"
            });

            $(".radio").unbind("click").bind("click", function(e){
                $(".radio").removeClass("active");
                $(e.currentTarget).addClass("active");
                var arriveTime = $(e.currentTarget).attr("value");
                $scope.data.arriveTime = arriveTime;
            });
        };

        $scope.init();

        $scope.addOrderCommit = function() {
            var taskTitle = $('.problemTitle').val();
            if(taskTitle == null || taskTitle.length == 0) {
                alert("请输入标题");
                return;
            }
            var cellphone = $('.problemPhone').val();
            if(cellphone == null || cellphone.length == 0) {
                alert("请输入手机号");
                return;
            }
            var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
            if(!myreg.test(cellphone)) {
                alert("请输入有效手机号");
                return;
            }
            var prepayFee = $('.problemMoney').val()
            if(prepayFee == null || prepayFee.length == 0) {
                alert("请输入金额");
                return;
            }
            if(prepayFee < 50) {
                alert("预约金额需要大于50");
                return;
            }
            var addressDetail = $('.problemAddress').val();
            if(addressDetail == null || addressDetail.length == 0) {
                alert("请输入详细地址");
                return;
            }
            var errorTime = $('.problemTime').val();
            if(errorTime == null || errorTime.length == 0) {
                alert("请输入故障发生时间");
                return;
            }
            var pos = $rootScope.user.address;
            var province = pos.substr(0, 2);
            var city = pos.substr(2, 2);
            var params = {
                taskTitle: taskTitle,
                category: $('.problemCategory').val(),
                taskDesc: $('.problem_desc').val(),
                cellphone: cellphone,
                prepayFee: prepayFee,
                addressDetail: addressDetail,
                province:province,
                city: province,
                county: '',
                deadline: new Date($('.problemStopTime').val()).getTime(),
                errorTime: new Date(errorTime).getTime()
            }

            $ajax.post({
                url: "/personCenter/savetask",
                data: params,
                success: function(d){
                    $navigate.go('/repairTask');
                },
                error: function(e){
                    console.log(e);
                }
            });
        };
    });
});