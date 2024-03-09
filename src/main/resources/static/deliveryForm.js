function submitForm(format) {
    const form = document.getElementById('deliveryForm');
    if (format === 'json') {
        form.action = '/result/json';
    } else {
        form.action = '/result';
    }
    form.submit();
}