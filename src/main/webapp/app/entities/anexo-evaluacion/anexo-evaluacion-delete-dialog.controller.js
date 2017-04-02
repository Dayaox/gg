(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('Anexo_evaluacionDeleteController',Anexo_evaluacionDeleteController);

    Anexo_evaluacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Anexo_evaluacion'];

    function Anexo_evaluacionDeleteController($uibModalInstance, entity, Anexo_evaluacion) {
        var vm = this;

        vm.anexo_evaluacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Anexo_evaluacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
