(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('PrevencionDialogController', PrevencionDialogController);

    PrevencionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Prevencion', 'Anexo_prevencion', 'Peticion'];

    function PrevencionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Prevencion, Anexo_prevencion, Peticion) {
        var vm = this;

        vm.prevencion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.anexo_prevencions = Anexo_prevencion.query();
        vm.peticions = Peticion.query({filter: 'prevencion-is-null'});
        $q.all([vm.prevencion.$promise, vm.peticions.$promise]).then(function() {
            if (!vm.prevencion.peticion || !vm.prevencion.peticion.id) {
                return $q.reject();
            }
            return Peticion.get({id : vm.prevencion.peticion.id}).$promise;
        }).then(function(peticion) {
            vm.peticions.push(peticion);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.prevencion.id !== null) {
                Prevencion.update(vm.prevencion, onSaveSuccess, onSaveError);
            } else {
                Prevencion.save(vm.prevencion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opleSioeApp:prevencionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
