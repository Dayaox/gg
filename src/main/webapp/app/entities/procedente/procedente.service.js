(function() {
    'use strict';
    angular
        .module('opleSioeApp')
        .factory('Procedente', Procedente);

    Procedente.$inject = ['$resource'];

    function Procedente ($resource) {
        var resourceUrl =  'api/procedentes/:id';

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
