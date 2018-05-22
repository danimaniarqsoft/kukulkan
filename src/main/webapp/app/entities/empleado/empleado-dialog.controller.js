(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('EmpleadoDialogController', EmpleadoDialogController);

    EmpleadoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Empleado', 'Direccion', 'Trabajo', 'Departamento', 'Tarea'];

    function EmpleadoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Empleado, Direccion, Trabajo, Departamento, Tarea) {
        var vm = this;

        vm.empleado = entity;
        vm.clear = clear;
        vm.save = save;
        vm.addresses = Direccion.query({filter: 'empleado-is-null'});
        $q.all([vm.empleado.$promise, vm.addresses.$promise]).then(function() {
            if (!vm.empleado.address || !vm.empleado.address.id) {
                return $q.reject();
            }
            return Direccion.get({id : vm.empleado.address.id}).$promise;
        }).then(function(address) {
            vm.addresses.push(address);
        });
        vm.trabajos = Trabajo.query();
        vm.departamentos = Departamento.query();
        vm.tareas = Tarea.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.empleado.id !== null) {
                Empleado.update(vm.empleado, onSaveSuccess, onSaveError);
            } else {
                Empleado.save(vm.empleado, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kukulkanApp:empleadoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
