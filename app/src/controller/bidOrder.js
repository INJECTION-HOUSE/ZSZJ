define(['angular', 'menu'], function(angular){
    angular.module('bidOrder-controller', ['menu']).controller('bidOrderController', function($scope, $ajax){
        $scope.text = "中标订单";

        $scope.init = function() {
            $scope.loadWinBiddingTasks();
        };

        $scope.dataList = [];
        $scope.loadWinBiddingTasks = function() {
            $ajax.get({
                url: "/personCenter/listWinBiddingTask",
                type: "GET",
                data: {pageIndex: 0},
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    // TODO 解决分页问题
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

        $scope.getBidOrderStatus = function(status) {
            var statusDesc = "上门维修中";
            switch (status) {
                case 3:
                    statusDesc = "完工";
                    break;
                case 4:
                    statusDesc = "确认付款（评价）";
                    break;
                case 5:
                    statusDesc = "结束";
                    break;
            }
            return statusDesc;
        };

        $scope.init();
    });
});