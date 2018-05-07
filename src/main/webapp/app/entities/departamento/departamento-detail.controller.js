(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('DepartamentoDetailController', DepartamentoDetailController);

    DepartamentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Departamento', 'Empleado'];

    function DepartamentoDetailController($scope, $rootScope, $stateParams, previousState, entity, Departamento, Empleado) {
        var vm = this;

        vm.departamento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kukulkanApp:departamentoUpdate', function(event, result) {
            vm.departamento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
