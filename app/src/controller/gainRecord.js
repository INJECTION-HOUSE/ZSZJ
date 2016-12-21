define(['angular', 'menu'], function(angular){
    angular.module('gainRecord-controller', ['menu']).controller('gainRecordController', function($scope, $ajax){
        $scope.text = "收益记录";

        $scope.init = function() {
            $scope.loadIncomeRecords();
        }

        // TODO 获取提现金额
        $scope.availcash = "800";

        $scope.dataList = [];
        $scope.loadIncomeRecords = function() {
            // TODO 解决分页问题
            $ajax.get({
                url: "/personCenter/listMyIncomeRecords",
                data: {pageIndex: 0},
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    $scope.dataList = data.model;
                }
            });
        };

        $scope.dateFormatter = function(value) {
            var formattedDate = new Date(parseInt(value));
            var d = formattedDate.getDate();
            var m =  formattedDate.getMonth();
            m += 1;  // JavaScript months are 0-11
            var y = formattedDate.getFullYear();
            // var hhmmss = formattedDate.getHours()+":"+formattedDate.getMinutes()+":"+formattedDate.getSeconds();
            return y + "-" + m + "-" + d;
        };

        $scope.getIncomeType = function(status) {
            if(status) {
                return "已提现";
            } else {
                return "现金收入";
            }
        };

        $scope.getSourceFrom = function(from) {
            var result = '';
            switch (from) {
                case 1:
                    result = '微信端';
                    break;
                default:
                    result = 'PC端';
                    break;
            }
            return result;
        };

        $scope.init();
    });
});