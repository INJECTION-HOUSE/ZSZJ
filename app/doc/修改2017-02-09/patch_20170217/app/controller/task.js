define(['angular', 'menu', 'mobiscroll', 'dragRefresh'], function(angular){
    angular.module('task-controller', ['menu','drag-refresh']).controller('taskController', function($scope, $rootScope, $navigate, $timeout, $ajax){
        $scope.taskList = [];
        $scope.search = {
            type: 1,
            pos: ""
        };

        $scope.init = function(){
            var pos = getUserPos($rootScope.user.address);
            $scope.user = {
                userName: $rootScope.user.nickname || "",
                headimgurl : $rootScope.user.headimgurl || "",
                city: pos.city,
                isValid: $rootScope.user.valid
            };
            $scope.search.pos = pos.province + pos.city;
            $scope.loadData($scope.search.type);
        };

        $scope.loadData = function(type){
            $scope.search.type = type;
            $ajax.get({
                url: "/task/getTaskListForApp",
                data: $scope.search,
                success:  function(d){
                    $scope.taskList = d.data;
                },
                error: function(e){
                    console.log(e);
                }
            });
        };

        $scope.addTask = function(){
            if($scope.user.isValid){
                $navigate.go('/addOrder');
            }else{
                alert("您尚未绑定手机号，请点击绑定手机号按钮或者前往个人中心进行绑定。");
            }
        };

        $scope.doRegister = function(){
            $navigate.go('/bindUser');
        };

        $scope.formatDate = function(msc){
            var date = new Date(parseInt(msc));
            return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
        };

        $scope.getTypeText = function(type){
            return "维修类型";
        };

        $scope.getStatusText = function(status){
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

        function getUserPos(pos){
            var res = {country: "", province: "", city: ""};
            if(pos){
                var p = pos.split(" ");
                res.country = p[0] || "";
                res.province = p[1] || "";
                res.city = p[2] || "";
            }
            return res;
        };

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

        $scope.taskDetail = function(id){
            $navigate.go('/taskDetail/' + id);
        }

        $scope.hasBid = function(joiner){
            return joiner ? joiner.indexOf($scope.user.id) != -1 : false;
        }

        $scope.init();
    });
});