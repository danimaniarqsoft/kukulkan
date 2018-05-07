(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('DireccionDetailController', DireccionDetailController);

    DireccionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Direccion'];

    function DireccionDetailController($scope, $rootScope, $stateParams, previousState, entity, Direccion) {
        var vm = this;

        vm.direccion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kukulkanApp:direccionUpdate', function(event, result) {
            vm.direccion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
