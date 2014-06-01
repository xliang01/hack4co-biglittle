[
    '{{repeat(10)}}',
    {
        title: '{{lorem(1, "sentences")}}',
         location: '{{lorem(1, "sentences")}}',
        minParticipants: '{{integer(0, 30)}}',
         maxParticipants: '{{integer(0, 30)}}',
        active: '{{bool()}}',
        description: '{{lorem(1, paragraphs)}}',
        startTime: '{{date(new Date(2014, 0, 1), new Date(), "YYYY-MM-ddThh:mm:ss")}}',
        endTime: '{{date(new Date(2014, 0, 1), new Date(), "YYYY-MM-ddThh:mm:ss")}}',
        createdAt: '{{date(new Date(2014, 0, 1), new Date(), "YYYY-MM-ddThh:mm:ss")}}'
    }
]