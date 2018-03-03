(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('PersonaController', PersonaController);

    PersonaController.$inject = ['DataUtils', 'Persona'];

    function PersonaController(DataUtils, Persona) {

        var vm = this;

        vm.personas = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Persona.query(function(result) {
                vm.personas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
