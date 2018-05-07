(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('TareaDeleteController',TareaDeleteController);

    TareaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tarea'];

    function TareaDeleteController($uibModalInstance, entity, Tarea) {
        var vm = this;

        vm.tarea = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tarea.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
