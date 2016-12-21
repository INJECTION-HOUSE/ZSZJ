define(['angular', 'menu'], function(angular){
    angular.module('bidRecord-controller', ['menu']).controller('bidRecordController', function($scope, $ajax){
        $scope.text = "投标记录";

        $scope.init = function() {
            $scope.loadBidRecord();
        };

        $scope.dataList = [];
        $scope.loadBidRecord = function() {
            $ajax.get({
                url: "/personCenter/listMyBidRecords",
                data: {pageIndex: 0},
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    // TODO 解决分页问题
                    $scope.dataList = data.model;
                }
            });
        }

        $scope.getBidRecordStatus = function(bingBid, taskstatus) {
            var bidstatus = "";
            if(!bingBid) {
                if(taskstatus == 0) {
                    bidstatus = "雇主选标中";
                } else {
                    bidstatus = "竞标失败";
                }
            }
            else {
                bidstatus = "中标";
            }
            return bidstatus;
        };

        $scope.getBidRecordStatusImg = function(bingBid, taskstatus) {
            var bidstatus = "./images/icons/bid_record_xd.png";
            if(!bingBid) {
                if(taskstatus == 0) {
                    bidstatus = "./images/icons/bid_record_xd.png";
                } else {
                    bidstatus = "./images/icons/bid_record_sb.png";
                }
            }
            else {
                bidstatus = "./images/icons/bid_record_xd.png";
            }
            return bidstatus;
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

        $scope.init();
    });
});