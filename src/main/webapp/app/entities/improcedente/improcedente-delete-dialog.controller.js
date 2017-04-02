(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('ImprocedenteDeleteController',ImprocedenteDeleteController);

    ImprocedenteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Improcedente'];

    function ImprocedenteDeleteController($uibModalInstance, entity, Improcedente) {
        var vm = this;

        vm.improcedente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Improcedente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
