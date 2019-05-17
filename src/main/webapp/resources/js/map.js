"use strict";

let path = "/logistics/orders";

let mymap = L.map('mapid');
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox.streets',
    accessToken: 'pk.eyJ1IjoiY2hpcm9rZXkiLCJhIjoiY2p1cGs1OThuMG5neTQzc2o3dWNpd3J2dSJ9.3oKnbCg0rRBAyGMtVleLCw'
}).addTo(mymap);

document.addEventListener("DOMContentLoaded", getLocations);
function getLocations() {
    let url = new URL(window.location.origin + path + '/waypointsCitiesLocations');
    let params = {orderID: document.getElementById("orderID").innerText};
    url.search = new URLSearchParams(params);
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (array) {
            mymap.setView([array[0].y, array[0].x], 5);
            for (let i = 0; i < array.length; i++) {
                L.marker([array[i].y, array[i].x]).addTo(mymap);
            }
        }).catch(error => console.error('Error:', error));
}
