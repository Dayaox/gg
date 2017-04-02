(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('EvaluacionDialogController', EvaluacionDialogController);

    EvaluacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Evaluacion', 'Anexo_evaluacion', 'Procedente', 'Improcedente', 'Presentacion', 'Peticion'];

    function EvaluacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Evaluacion, Anexo_evaluacion, Procedente, Improcedente, Presentacion, Peticion) {
        var vm = this;

        vm.evaluacion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.anexo_evaluacions = Anexo_evaluacion.query();
        vm.procedentes = Procedente.query();
        vm.improcedentes = Improcedente.query();
        vm.presentacions = Presentacion.query();
        vm.peticions = Peticion.query({filter: 'evaluacion-is-null'});
        $q.all([vm.evaluacion.$promise, vm.peticions.$promise]).then(function() {
            if (!vm.evaluacion.peticion || !vm.evaluacion.peticion.id) {
                return $q.reject();
            }
            return Peticion.get({id : vm.evaluacion.peticion.id}).$promise;
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
            if (vm.evaluacion.id !== null) {
                Evaluacion.update(vm.evaluacion, onSaveSuccess, onSaveError);
            } else {
                Evaluacion.save(vm.evaluacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opleSioeApp:evaluacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
