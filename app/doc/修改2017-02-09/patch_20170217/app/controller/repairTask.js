define(['angular', 'menu'], function(angular){
    angular.module('repairTask-controller', ['menu']).controller('repairTaskController', function($scope, $rootScope, $ajax){
        $scope.text = "维修任务";

        $scope.init = function() {
            $scope.getTaskList();
        };

        $scope.dataList = [];
        $scope.getTaskList = function() {
            // TODO 获取用户ID 和 分页
            var userId = $rootScope.user.id;
            $ajax.get({
                url : "/task/getTaskList",
                cache : false,
                data : {memberId: userId},
                success : function(d) {
                    $scope.dataList = d.data;
                }
            });
        };

        $scope.getStatus = function(status){
            var result = '';
            switch(status){
                case 0:
                    result = '投标中';
                    break;
                case 1:
                    result = '选标中';
                    break;
                case 2:
                    result = '维修中';
                    break;
                case 3:
                    result = '已完工';
                    break;
                case 4:
                    result = '已付款';
                    break;
                case 5:
                    result = '已结束';
                    break;
                case 6:
                    result = '纠纷';
                    break;
                default:
                    result = '投标中';
                    break;
            }
            return result;
        };

        $scope.getDeadline = function(msc){
            var result = "";
            var sub = parseInt(msc) - (new Date().getTime());
            if(sub <= 0){
                result = "已截止";
            }else if(sub < 86400000){
                if(sub < 3600000){
                    result = Math.round(sub/60000) + "分钟后截止";
                }else{
                    result = Math.floor(sub/3600000) + "小时后截止";
                }
            }else{
                result = Math.floor(sub/86400000) + "天后截止";
            }
            return result;
        };

        $scope.getJoiner = function(joiner){
            if(joiner === null){
                return 0;
            }else{
                return joiner.split(',').length;
            }
        }

        $scope.getTaskCategory = function(type){
            var res = "";
            type = parseInt(type);
            switch(type){
                case 1:
                    res = "常见故障";
                    break;
                case 2:
                    res = "机械故障";
                    break;
                case 3:
                    res = "电子故障";
                    break;
                case 4:
                    res = "液压故障";
                    break;
                case 5:
                    res = "电气故障";
                    break;
                case 6:
                    res = "辅助故障";
                    break;
                default:
                    res = "常见故障";
                    break;
            }
            return res;
        };

        $scope.init();
    });
});