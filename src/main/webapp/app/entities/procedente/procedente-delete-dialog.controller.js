(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('ProcedenteDeleteController',ProcedenteDeleteController);

    ProcedenteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Procedente'];

    function ProcedenteDeleteController($uibModalInstance, entity, Procedente) {
        var vm = this;

        vm.procedente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Procedente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
