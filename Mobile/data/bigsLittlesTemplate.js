[
    '{{repeat(10)}}',
    {
        firstName: '{{firstName()}}',
        lastName: '{{surname()}}',
        email: '{{email()}}',
        address: {
            line1: '{{integer(100, 999)}} {{street()}}',
            line2: '',
            city: '{{city()}}',
            state: '{{state()}}',
            zip: '{{integer(100, 10000)}}'
        }
        }
]