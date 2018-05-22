(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('TrabajoDetailController', TrabajoDetailController);

    TrabajoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Trabajo', 'Empleado'];

    function TrabajoDetailController($scope, $rootScope, $stateParams, previousState, entity, Trabajo, Empleado) {
        var vm = this;

        vm.trabajo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kukulkanApp:trabajoUpdate', function(event, result) {
            vm.trabajo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
