(function() {
    'use strict';
    angular
        .module('opleSioeApp')
        .factory('Improcedente', Improcedente);

    Improcedente.$inject = ['$resource'];

    function Improcedente ($resource) {
        var resourceUrl =  'api/improcedentes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
