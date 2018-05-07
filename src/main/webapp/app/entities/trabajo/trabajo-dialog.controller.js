(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('TrabajoDialogController', TrabajoDialogController);

    TrabajoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trabajo', 'Empleado', 'Tarea'];

    function TrabajoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Trabajo, Empleado, Tarea) {
        var vm = this;

        vm.trabajo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.empleados = Empleado.query();
        vm.tareas = Tarea.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.trabajo.id !== null) {
                Trabajo.update(vm.trabajo, onSaveSuccess, onSaveError);
            } else {
                Trabajo.save(vm.trabajo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kukulkanApp:trabajoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
