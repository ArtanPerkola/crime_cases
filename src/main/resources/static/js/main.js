document.addEventListener('DOMContentLoaded', function() {
    fetchData('defendants', 'defendants-list');
    fetchData('crimes', 'crimes-list');
    fetchData('indictments', 'indictments-list');
    fetchData('singles', 'singles-list');
    fetchData('multiples', 'multiples-list');
});

function fetchData(endpoint, elementId) {
    fetch(`http://localhost:8080/api/${endpoint}`)
        .then(response => response.json())
        .then(data => {
            const listElement = document.getElementById(elementId);
            data.forEach(item => {
                const listItem = document.createElement('li');
                listItem.classList.add('list-group-item');
                listItem.textContent = JSON.stringify(item);
                listElement.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error fetching data:', error));
}
