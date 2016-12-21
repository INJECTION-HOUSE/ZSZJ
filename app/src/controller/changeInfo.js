define(['angular', 'menu'], function(angular){
    angular.module('changeInfo-controller', ['menu']).controller('changeInfoController', function($scope, $rootScope, $ajax){
        $scope.text = "修改个人信息";


        $scope.init = function() {
            $scope.nickname = $rootScope.user.nickname;
            $scope.gender = $rootScope.user.gender;
            $scope.birthday = $rootScope.user.birthday;
            $scope.telephone = $rootScope.user.telephone;
            $scope.qqNumber = $rootScope.user.qqNumber;
            $scope.workAddress = $rootScope.user.workAddress;
            $scope.currentAddress = $rootScope.user.currentAddress;
            $scope.occupy = $rootScope.user.occupy;
            $scope.introdDesc = $rootScope.user.introdDesc;
            $scope.skills = $rootScope.user.skills;
            $scope.id = $rootScope.user.id;
            $scope.memeberId = $rootScope.user.memeberId;
        };

        $scope.init();

        $scope.saveInfo = function() {
            $ajax.post({
                url:"/profile/updateDetail",
                cache:false,
                data: $('#updateDetail').serialize(),
                dataType: 'json',
                success:function(data){
                    alert('修改成功');
                }
            })
        };

        $(".select").mobiscroll().select({
            lang: "zh",
            theme: "mobiscroll",
            display: "bottom"
        });

        $(".datetimepicker").mobiscroll().date({
            lang: "zh",
            theme: "mobiscroll",
            display: "bottom"
        });

    });
});