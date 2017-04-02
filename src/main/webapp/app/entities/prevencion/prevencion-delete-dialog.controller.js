(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('PrevencionDeleteController',PrevencionDeleteController);

    PrevencionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Prevencion'];

    function PrevencionDeleteController($uibModalInstance, entity, Prevencion) {
        var vm = this;

        vm.prevencion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Prevencion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
