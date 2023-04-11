window.addEventListener('DOMContentLoaded', () => {
	let cells = document.querySelectorAll('td[contenteditable="true"]');
	cells.forEach((cell) => {
	    const rowIndex = cell.parentNode.rowIndex;
		const columnName = cell.getAttribute('data-column-name');
		const tableId = cell.closest('table').getAttribute('id');

		const key = `${tableId}-${columnName}-${rowIndex}`;
		const savedValue = localStorage.getItem(key);
		if (savedValue !== null) {
		    cell.innerText = savedValue;
		}
	});

	cells = document.querySelectorAll('td[contenteditable="true"]');
	    cells.forEach((cell) => {
		    cell.addEventListener('blur', () => {
			    const newValue = cell.innerText;
				const rowIndex = cell.parentNode.rowIndex;
				const columnName = cell.getAttribute('data-column-name');
				const tableId = cell.closest('table').getAttribute('id');

				const key = `${tableId}-${columnName}-${rowIndex}`;
				localStorage.setItem(key, newValue);

				cell.innerText = newValue;
			});
		});

		const savedCells = localStorage.getItem('my-table');
		if (savedCells !== null) {
			const parsedCells = JSON.parse(savedCells);
			for (const key in parsedCells) {
				const cell = document.querySelector(`#${key}`);
				if (cell !== null) {
					cell.innerText = parsedCells[key];
				}
			}
		}
	});

	window.addEventListener('beforeunload', () => {
		const cells = document.querySelectorAll('td[contenteditable="true"]');
		const savedCells = {};
		cells.forEach((cell) => {
			const rowIndex = cell.parentNode.rowIndex;
			const columnName = cell.getAttribute('data-column-name');
			const tableId = cell.closest('table').getAttribute('id');

			const key = `${tableId}-${columnName}-${rowIndex}`;
			const value = cell.innerText;
			savedCells[key] = value;
		});

		localStorage.setItem('my-table', JSON.stringify(savedCells));
	});