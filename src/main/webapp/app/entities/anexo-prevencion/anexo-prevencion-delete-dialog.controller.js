(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('Anexo_prevencionDeleteController',Anexo_prevencionDeleteController);

    Anexo_prevencionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Anexo_prevencion'];

    function Anexo_prevencionDeleteController($uibModalInstance, entity, Anexo_prevencion) {
        var vm = this;

        vm.anexo_prevencion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Anexo_prevencion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
