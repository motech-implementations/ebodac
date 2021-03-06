if(!$('#jqueryInputMaskJs').length) {
    var s = document.createElement("script");
    s.id = "jqueryInputMaskJs";
    s.type = "text/javascript";
    s.src = "../ebodac/resources/js/jquery.inputmask.js";
    $("head").append(s);
}

if(!$('#inputMaskJs').length) {
    var s = document.createElement("script");
    s.id = "inputMaskJs";
    s.type = "text/javascript";
    s.src = "../ebodac/resources/js/inputmask.js";
    $("head").append(s);
}

if ($scope.selectedEntity.name === "Participant" || $scope.selectedEntity.name === "Visit") {
    $scope.showBackToEntityListButton = false;
} else {
    $scope.showViewTrashButton = false;
    $scope.showImportButton = false;
    $scope.backToEntityList = function() {
        $scope.dataRetrievalError = false;
        innerLayout({
            spacing_closed: 30,
            east__minSize: 200,
            east__maxSize: 350
        });
        $scope.selectedEntity = undefined;
        window.location.replace('#/ebodac/reports');
    };
}

if ($scope.selectedEntity.name === "ReportPrimerVaccination" || $scope.selectedEntity.name === "ReportBoosterVaccination") {
    $scope.showFieldsButton = false;
    $scope.availableExportFormats = ['csv','pdf','xls'];
    var exportEntityModal = '../ebodac/resources/partials/modals/export-entity.html';
    $scope.customModals.push(exportEntityModal);
    $scope.exportEntityInstances = function () {
        $('#exportEbodacInstanceModal').modal('show');
    };
}

if ($scope.selectedEntity.name === "Participant") {
    $rootScope.selectedTab = "subjects";
} else if ($scope.selectedEntity.name === "Visit") {
    $rootScope.selectedTab = "visits";
} else {
    $rootScope.selectedTab = "reports";
}

$scope.showAddInstanceButton = false;
$scope.showDeleteInstanceButton = false;
var importCsvModal = '../ebodac/resources/partials/modals/import-csv.html';
var editSubjectModal = '../ebodac/resources/partials/modals/edit-subject.html';

$scope.customModals.push(importCsvModal);
$scope.customModals.push(editSubjectModal);

$scope.getMessageFromData = function(responseData) {
    var messageCode, messageParams;

    if (responseData && (typeof(responseData) === 'string')) {
        if (responseData.startsWith('key:')) {
            if (responseData.indexOf('params:') !== -1) {
               messageCode = responseData.split('\n')[0].split(':')[1];
               messageParams = responseData.split('\n')[1].split(':')[1].split(',');
            } else {
               messageCode = responseData.split(':')[1];
            }
        } else {
            messageCode = responseData;
        }
    }

    return $scope.msg(messageCode, messageParams);
};

$scope.showLookupDialog = function() {
    $("#lookup-dialog")
    .css({'top': ($("#lookupDialogButton").offset().top - $("#main-content").offset().top) - 40,
    'left': ($("#lookupDialogButton").offset().left - $("#main-content").offset().left) - 15})
    .toggle();
    $("div.arrow").css({'left': 50});
};

$scope.importEntityInstances = function() {
    $('#importSubjectModal').modal('show');
};

$scope.importSubject = function () {
    blockUI();

    $('#importSubjectForm').ajaxSubmit({
        success: function () {
            $("#instancesTable").trigger('reloadGrid');
            $('#importSubjectForm').resetForm();
            $('#importSubjectModal').modal('hide');
            unblockUI();
        },
        error: function (response) {
            handleResponse('mds.error', 'mds.error.importCsv', response);
        }
    });
};

$scope.closeImportSubjectModal = function () {
    $('#importSubjectForm').resetForm();
    $('#importSubjectModal').modal('hide');
};

$scope.closeExportEbodacInstanceModal = function () {
    $('#exportEbodacInstanceForm').resetForm();
    $('#exportEbodacInstanceModal').modal('hide');
};

$scope.exportInstance = function() {
    var selectedFieldsName = [], url, sortColumn, sortDirection;

    url = "../ebodac/entities/" + $scope.selectedEntity.id + "/exportInstances";
    url = url + "?outputFormat=" + $scope.exportFormat;
    url = url + "&exportRecords=" + $scope.actualExportRecords;

   if ($scope.actualExportColumns === 'selected') {
       angular.forEach($scope.selectedFields, function(selectedField) {
           selectedFieldsName.push(selectedField.basic.displayName);
       });

       url = url + "&selectedFields=" + selectedFieldsName;
   }

   if ($scope.checkboxModel.exportWithOrder === true) {
       sortColumn = $('#instancesTable').getGridParam('sortname');
       sortDirection = $('#instancesTable').getGridParam('sortorder');

       url = url + "&sortColumn=" + sortColumn;
       url = url + "&sortDirection=" + sortDirection;
   }

   if ($scope.checkboxModel.exportWithLookup === true) {
       url = url + "&lookup=" + (($scope.selectedLookup) ? $scope.selectedLookup.lookupName : "");
       url = url + "&fields=" + JSON.stringify($scope.lookupBy);
   }

    $http.get(url)
    .success(function () {
        if ($scope.selectedEntity.name === "ReportPrimerVaccination" || $scope.selectedEntity.name === "ReportBoosterVaccination") {
            $('#exportEbodacInstanceForm').resetForm();
            $('#exportEbodacInstanceModal').modal('hide');
        } else {
            $('#exportInstanceForm').resetForm();
            $('#exportInstanceModal').modal('hide');
        }
        window.location.replace(url);
    })
    .error(function (response) {
        handleResponse('mds.error', 'mds.error.exportData', response);
    });
};

$scope.saveCurrentRecord = function() {
    $scope.currentRecord.$save(function() {
        $scope.unselectInstance();
        unblockUI();
    }, angularHandler('mds.error', 'mds.error.cannotAddInstance'));
}

$scope.addEntityInstanceDefault = function () {
    blockUI();

    var entityObject = {};

    var values = $scope.currentRecord.fields;
    angular.forEach (values, function(value, key) {
        value.value = value.value === 'null' ? null : value.value;

        if (value.name === "changed") {
            value.value = true;
        }

        if (!$scope.isAutoGenerated(value)) {
            entityObject[value.name] = value.value;
        }
    });

    if ($scope.selectedEntity.name === "Visit" && $scope.selectedInstance !== undefined) {
        motechConfirm("ebodac.reenrollVisit.confirmMsg", "ebodac.reenrollVisit.confirmTitle",
            function (response) {
                if (!response) {
                    unblockUI();
                    return;
                }
                $http.post('../ebodac/reenrollSubject', entityObject)
                .success(function(response) {
                    motechAlert(response, "ebodac.reenrollVisit.successTitle");
                    $scope.saveCurrentRecord();
                })
                .error(function(response) {
                    motechAlert("ebodac.reenrollVisit.errorMsg", "ebodac.reenrollVisit.errorTitle", $scope.getMessageFromData(response));
                    unblockUI();
                });
            });

    } else if ($scope.selectedEntity.name === "Participant" && $scope.selectedInstance !== undefined) {
        $http.post('../ebodac/subjectDataChanged', entityObject)
        .success(function(response) {
            $scope.saveCurrentRecord();
        })
        .error(function(response) {
            motechAlert("ebodac.updateSubject.errorMsg", "ebodac.updateSubject.errorTitle", $scope.getMessageFromData(response));
            unblockUI();
        });
    } else {
        $scope.saveCurrentRecord();
    }
};

$scope.addEntityInstance = function() {
    if ($scope.selectedEntity.name === "Participant") {
        var input = $("#phoneNumberForm");
        var fieldValue = input.val();
        if (fieldValue !== null && fieldValue !== undefined && fieldValue !== '') {
            input.val(fieldValue.replace(/ /g, ''));
            input.trigger('input');
        }

        $http.get('../ebodac/ebodac-config')
        .success(function(response){
            if(response.showWarnings) {
                $('#editSubjectModal').modal('show');
            } else {
                $scope.addEntityInstanceDefault();
            }
        })
        .error(function(response) {
            $('#editSubjectModal').modal('show');
        });
    } else {
        $scope.addEntityInstanceDefault();
    }
};

var isPhoneNumberForm = false;

$scope.loadEditValueFormDefault = $scope.loadEditValueForm;

$scope.loadEditValueForm = function (field) {
    if(field.name === 'phoneNumber') {
        isPhoneNumberForm = true;
        return '../ebodac/resources/partials/widgets/field-phone-number.html';
    } else if (field.name === 'visits') {
        return '../ebodac/resources/partials/widgets/field-visits.html';
    } else if (field.name === 'enrollment') {
        return '../ebodac/resources/partials/widgets/field-enrollment.html';
    }

    if(isPhoneNumberForm) {
        $("#phoneNumberForm").inputmask({ mask: "999 999 999[ 999]", greedy: false, autoUnmask: true });
        isPhoneNumberForm = false;
    }

    return $scope.loadEditValueFormDefault(field);
};

$scope.retrieveAndSetEntityData = function(entityUrl, callback) {
    $scope.lookupBy = {};
    $scope.selectedLookup = undefined;
    $scope.lookupFields = [];
    $scope.allEntityFields = [];

    blockUI();

    $http.get(entityUrl).success(function (data) {
        $scope.selectedEntity = data;

        $scope.setModuleEntity($scope.selectedEntity.module, $scope.selectedEntity.name);

        $http.get('../ebodac/entities/'+$scope.selectedEntity.name+'/'+$scope.selectedEntity.id+'/entityFields').success(function (data) {
            $scope.allEntityFields = data;
            $scope.setAvailableFieldsForDisplay();

            if ($routeParams.entityId === undefined) {
                var hash = window.location.hash.substring(2, window.location.hash.length) + "/" + $scope.selectedEntity.id;
                $location.path(hash);
                $location.replace();
                window.history.pushState(null, "", $location.absUrl());
            }

            Entities.getAdvancedCommited({id: $scope.selectedEntity.id}, function(data) {
                $scope.entityAdvanced = data;
                $rootScope.filters = [];
                $scope.setVisibleIfExistFilters();

                if ($scope.selectedEntity.name === "Visit") {
                    $http.get("../ebodac/getLookupsForVisits")
                    .success(function(data) {
                        $scope.entityAdvanced.indexes = data;
                    });
                } else if ($scope.selectedEntity.name === "Participant") {
                    $http.get("../ebodac/getLookupsForSubjects")
                    .success(function(data) {
                        $scope.entityAdvanced.indexes = data;
                    });
                } else {
                    $(".clearfix").children(".btn-primary").html("<i class='fa fa-lg fa-level-up'></i>&nbsp;" + $scope.msg('ebodac.web.reports.btn.backToList'));
                }

                var filterableFields = $scope.entityAdvanced.browsing.filterableFields,
                    i, field, types, fieldName;
                for (i = 0; i < $scope.allEntityFields.length; i += 1) {
                    field = $scope.allEntityFields[i];

                    if ($.inArray(field.id, filterableFields) >= 0) {
                        types = $scope.filtersForField(field);

                        $rootScope.filters.push({
                            displayName: field.basic.displayName,
                            type: field.type.typeClass,
                            field: field.basic.name.split('.')[0],
                            types: types
                        });
                    }
                }
                $scope.selectedFields = [];
                for (i = 0; i < $scope.allEntityFields.length; i += 1) {
                    field = $scope.allEntityFields[i];
                    fieldName = field.basic.name.split('.')[0];
                    if ($.inArray(fieldName, $scope.entityAdvanced.userPreferences.visibleFields) !== -1) {
                        $scope.selectedFields.push(field);
                    }
                }
                $scope.updateInstanceGridFields();

                if (callback) {
                    callback();
                }

                unblockUI();
            });
        });
        unblockUI();
    });
};

$scope.editInstance = function(id, module, entityName) {
    blockUI();
    $scope.setHiddenFilters();
    $scope.instanceEditMode = true;
    $scope.setModuleEntity(module, entityName);
    $scope.loadedFields = Instances.selectInstance({
        id: $scope.selectedEntity.id,
        param: id
        },
        function (data) {
            $scope.selectedInstance = id;
            $scope.currentRecord = data;
            $scope.fields = data.fields;

            if (entityName === "Participant") {
                var i;
                for (i = 0; i < $scope.fields.length; i += 1) {
                    if ($scope.fields[i].name === "changed") {
                        $scope.fields[i].nonDisplayable = true;
                    }
                }
            }

            unblockUI();
        }, angularHandler('mds.error', 'mds.error.cannotUpdateInstance'));
};

$scope.addFieldForDataBrowser = function(selected, checked) {
    var field, i,
        selectedName = selected.split('.')[0],
        updatePref = true,
        fieldsData = {
            field: selectedName,
            action: 'ADD'
        };

    if (!checked) {
        fieldsData.action = 'REMOVE';
        if ($scope.isFieldSelected(selected)) {
            for (i = 0; i < $scope.selectedFields.length; i += 1) {
                field = $scope.selectedFields[i];
                if (field.basic.name === selected) {
                    $scope.selectedFields.remove(i, i);
                }
            }

            for (i = 0; i < $scope.selectedFields.length; i += 1) {
                if ($scope.selectedFields[i].basic.name.split('.')[0] === selectedName) {
                    updatePref = false;
                }
            }
        }
    } else {
        if (!$scope.isFieldSelected(selected)) {
            for (i = 0; i < $scope.availableFieldsForDisplay.length; i += 1) {
                field = $scope.availableFieldsForDisplay[i];
                if (field.basic.name === selected) {
                    $scope.selectedFields.push(field);
                }
            }
        }
    }

    if (updatePref) {
        $http.post('../mds/entities/' + $scope.selectedEntity.id + "/preferences/fields", fieldsData)
        .error(function () {
            handleResponse('mds.error', 'mds.preferences.error.fields', '');
            unblockUI();
        });
    }
};
