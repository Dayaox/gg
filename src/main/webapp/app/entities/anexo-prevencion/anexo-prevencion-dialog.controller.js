(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('Anexo_prevencionDialogController', Anexo_prevencionDialogController);

    Anexo_prevencionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Anexo_prevencion', 'Prevencion'];

    function Anexo_prevencionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Anexo_prevencion, Prevencion) {
        var vm = this;

        vm.anexo_prevencion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.prevencions = Prevencion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.anexo_prevencion.id !== null) {
                Anexo_prevencion.update(vm.anexo_prevencion, onSaveSuccess, onSaveError);
            } else {
                Anexo_prevencion.save(vm.anexo_prevencion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opleSioeApp:anexo_prevencionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
