function loadPage() {
    $('#dataTable').DataTable({
        initComplete: function () {
            this.api()
                .columns()
                .every(function () {
                    var column = this;
                    var select = $('<select><option value=""></option></select>')
                        .appendTo($(column.footer()).empty())
                        .on('change', function () {
                            var val = $.fn.dataTable.util.escapeRegex($(this).val());

                            column.search(val ? '^' + val + '$' : '', true, false).draw();
                        });

                    column
                        .data()
                        .unique()
                        .sort()
                        .each(function (d, j) {
                            select.append('<option value="' + d + '">' + d + '</option>');
                        });
                })
        },
        "language": {
                    "url": "////cdn.datatables.net/plug-ins/1.13.4/i18n/uk.json"
                },
        "columnDefs": [
            { "searchable": false, "targets": 0 }
        ],
        "lengthMenu": [[100, 500, 1000, -1], [100, 500, 1000, "все"]],

    });
};

function s2ab(s) {
    var buf = new ArrayBuffer(s.length);
    var view = new Uint8Array(buf);
    for (var i = 0; i < s.length; i++) {
        view[i] = s.charCodeAt(i) & 0xFF;
    }
    return buf;
}

function exportTableToExcel(tableID, filename = 'excel-price') {
    var table = document.getElementById(tableID);
    var data = [];

    for (var i = 0, row; row = table.rows[i]; i++) {
        if (i === table.rows.length - 1) {
            break;
        }

        var rowData = [];
        for (var j = 0, col; col = row.cells[j]; j++) {
            rowData.push(col.innerText);
        }
        data.push(rowData);
    }

    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.aoa_to_sheet(data, {columns: [{width: 10}, {width: 20}, {width: 30}]});
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    XLSX.writeFile(wb, 'price.xlsx');
}

function clearColumnValues() {
    const table = document.getElementById('dataTable');
    const rows = table.getElementsByTagName('tr');

    for (let i = 1; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        const cell = cells[4]; // выбираем ячейку пятой колонки (с индексом 4, так как индексация начинается с 0)
        const rowIndex = cell.parentNode.rowIndex;
        const columnName = cell.getAttribute('data-column-name');
        const tableId = cell.closest('table').getAttribute('id');
        const key = `${tableId}-${columnName}-${rowIndex}`;

        cell.innerText = '';
        localStorage.setItem(key, '');
    }
}

function clearLocalStorage(){
    localStorage.clear();
}

