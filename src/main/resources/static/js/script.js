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
        "columnDefs": [
            { "searchable": false, "targets": 0 }
        ],
        "lengthMenu": [100, 500, 1000, 10000]
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

function exportTableToExcel(tableID, filename = 'excel-data') {
  var table = document.getElementById(tableID);

  var data = [];

  for (var i = 0, row; row = table.rows[i]; i++) {
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
  console.log(data);
}