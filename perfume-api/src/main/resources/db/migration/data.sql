insert into member (id, sex, username, email, password, role, promotion_consent, marketing_consent, created_at,
                    updated_at)
values (100, 'OTHER', 'admin', 'admin@admin.com', '$2a$10$JqiVSGED0ceSJj7qMwXRmO8Otam7IrvqY4j6K.mklPDgo54SP49sa',
        'USER', 0, 0,
        now(), now());

insert into category (id, name, description, tags, created_at, updated_at)
values (1, 'Fruity', '달콤한 과일의 향이 지속되어 생동감과 매력적인 느낌을 줍니다.', '#달달한 #과즙미', now(), now()),
       (2, 'Floral', '꽃 향기를 가득 담아 사랑스러운 느낌을 줍니다.', '#우아한 #사랑스러운 #꽃향기', now(), now()),
       (3, 'Woody', '나무 향을 의미하며, 건조하고 성숙한 느낌을 전달합니다.', '#차분함 #릴렉스', now(), now()),
       (4, 'Citrus', '상큼하고 톡 쏘는 향으로 가볍고 산뜻한 느낌을 줍니다.', '#상큼한 #질리지않는', now(), now()),
       (5, 'Sweet', '달달하고 크리미한 향으로 가볍고 중독적인 느낌을 줍니다.', '#달달함 #대중적 #행복감', now(), now()),
       (6, 'Green', '자연의 풀내음 향을 담아 싱그럽고 고급스러운 느낌을 줍니다.', '#자연스러운 #싱그러움', now(), now()),
       (7, 'Musk', '포근하고 부드러운 향으로 관능적인 느낌을 줍니다.', '#이불속 #포근한 #따뜻한', now(), now()),
       (8, 'Spicy', '매운듯한 자극적인 향으로 깊이감과 매혹적인 느낌을 줍니다.', '#톡쏘는 #독특함', now(), now()),
       (9, 'Oriental', '동양적인 향으로 성숙하고 강렬한 느낌을 줍니다.', '#동양미 #성숙함', now(), now()),
       (10, 'Aqua', '맑고 깨끗한 물의 향으로 상쾌하고 청량감 있는 느낌을 줍니다.', '#시원한 #여름휴가', now(), now()),
       (11, 'Animal', '동물적인 느낌 혹은 원초적인 본능을 이끌어내는 향으로 따뜻한 느낌을 줍니다.', '#독특함 #중독적 #이끌림', now(), now());

insert into file (id, url, created_at, updated_at)
values (1, "test-url.com", now(), now());

insert into brand (id, name, story, brand_url, thumbnail_id, created_at, updated_at)
values (1, '조말론', '조말론은 18세기 영국에서 시작된 럭셔리 향수 브랜드입니다. 영국 왕실의 향수 제조사로 시작하여, 현재는 럭셔리 향수 브랜드로 자리잡고 있습니다.',
        'https://www.jomalone.co.kr/', 1, now(), now()),
       (2, '메종 마르지엘라', '메종 마르지엘라는 1988년 파리에서 설립된 프랑스 패션 하우스입니다. 특유의 미니멀한 디자인과 독특한 향기로 많은 사랑을 받고 있습니다.',
        'https://www.maisonmargiela-fragrances.eu/', 1, now(), now()),
       (3, '바이레도', '바이레도(Byredo)는 추억과 감정을 제품과 경험으로 옮기겠다는 야심으로 벤 고햄(Ben Gorham)이 2006년 스톡홀름에서 설립한 유럽 럭셔리 브랜드입니다.',
        'https://www.byredo.com/eu_en/perfume/', 1,
        now(), now()),
       (4, '샤넬', '샤넬은 1910년 프랑스에서 시작된 럭셔리 패션 하우스이며, 향수 브랜드로도 유명하죠. 샤넬 No.5는 세계에서 가장 유명한 향수 중 하나입니다.','https://www.chanel.com/kr/fragrance/', 1, now(), now()),
       (5, '톰 포드', '21세기 진정한 럭셔리 브랜드를 추구하는 톰 포드의 향수는 고급스럽고 독특한 향으로 유명하며, 세련된 디자인과 높은 품질로 유명합니다.', 'https://www.tomford.com/beauty/fragrance/', 1, now(), now()),
       (6, '버버리', '영국의 대표적인 패션 브랜드인 버버리는 1856년 영국에서 시작되었습니다. 버버리의 향수는 영국적 향취를 담아 특유의 럭셔리한 느낌을 선보입니다.', 'https://kr.burberry.com/', 1, now(), now()),
       (7, '프라다', '프라다는 1913년 이탈리아에서 시작된 패션 브랜드입니다. 프라다의 향수는 독특한 향기와 세련된 보틀 디자인으로 유명합니다.', 'https://www.prada.com/kr/ko.html', 1, now(), now()),
       (8, '펜할리곤스', '영국 왕실이 사랑하는 펜할리곤스는 우리가 살고 있는 ‘현재’를 반영하는 브랜드입니다. 지금도 창의적 유산과 영국적 위트, 그리고 브랜드 철학이 그대로 이어지고 있습니다.', 'https://www.penhaligons.com/uk/en/', 1,
        now(), now()),
       (9, '프레쉬', '프레쉬는 1991년 미국에서 시작된 브랜드입니다. 프레쉬의 향수는 천연 원료를 사용하여 만들어지며, 특유의 청량한 느낌으로 유명합니다.', 'https://www.fresh.com/us/fragrance', 1, now(), now()),
       (11, '마크 제이콥스',
        '마크 제이콥스는 1984년 미국에서 시작된 패션 브랜드입니다. 특유의 밝고 젊은 분위기를 향수에 반영하여 ''데이지''를 비롯한 전세계 히트 향수 아이템을 선보이고 있습니다.', 'https://www.marcjacobs.com/default/the-marc-jacobs/fragrance/view-all-fragrance/', 1, now(),
        now()),
       (12, '산타마리아노벨라',
        '1221년 이탈리아 피렌체 수도원 정원에서 기원한 산타마리아노벨라는 800년 헤리티지를 계승하여 전통적인 생산 방식과 원료, 기술력을 결합하여 새로운 역사를 이어갑니다.', 'https://us.smnovella.com/collections/fragrances', 1, now(),
        now()),
       (13, '아쿠아 디파르마', '1916년부터 이탈리아 럭셔리를 상징하는 상징적인 향수와 코롱 컬렉션을 만들어 독특한 스타일을 키워온 브랜드입니다.', 'https://www.acquadiparma.com/default/en/', 1, now(), now()),
       (14, '논픽션',
        '논픽션은 향을 매개로 내면의 힘을 표현하는 라이프스타일 뷰티 브랜드입니다. 최상의 원료와 섬세한 조향을 바탕으로 신비롭고 독특한 무드를 빚어내는 향수, 하루의 시작과 끝을 더욱 정성스럽게 만드는 바디케어 제품군을 선보입니다.', 'https://nonfiction.com/',
        1, now(), now()),
       (15, '구찌', '1921년, 피렌체에서 설립된 세계적인 럭셔리 패션 브랜드 구찌는 창의성과 혁신, 이탈리아의 장인 정신으로 높은 명성을 얻고 있는 기업입니다.', 'https://www.gucci.com/us/en/', 1, now(), now()),
       (16, '르라보', '2006년 뉴욕에서 시작된 향수 브랜드로, 조향사의 연구실에서 일어나는 다양한 일에 영감을 받아 사람마다 특화된 향을 내는 향수를 만들어 나갑니다.', 'https://www.lelabofragrances.com/', 1, now(),
        now()),
       (17, '에르메스',
        '1837년에 태어난 프랑스의 럭셔리 브랜드로, 고급 가죽 상품과 패션 제품으로 유명합니다. 수제 공예의 전통과 우아함을 자랑하는 브랜드로, 최상의 품질과 디자인으로 세계적으로 인정받고 있습니다.', 'https://www.hermes.com/kr/ko/category/fragrances/#|',
        1,
        now(), now()),
       (18, '구딸 파리',
        '1945년 프랑스에서 탄생한 럭셔리 패션 브랜드로, 우아하고 세련된 디자인의 의류와 액세서리로 유명합니다. 오랜 전통과 프렌치 스타일을 현대적으로 재해석하여 세계적으로 사랑받고 있습니다.', 'https://us.goutalparis.com/', 1,
        now(), now()),
       (19, '끌로에', '1950년대에 탄생한 프랑스의 패션 하우스로, 고급스러운 컬렉션과 아름다운 장인 정신으로 유명합니다. 화려하고 세련된 디자인으로 패션계를 선도하며, 세계적인 명성을 자랑합니다.', 'https://www.chloe.com/us/fragrances',
        1,
        now(), now()),
       (20, '미우미우',
        '1993년 이탈리아에서 시작된 패션 브랜드로, 전통적이면서도 현대적이고 대담한 디자인으로 유명합니다. 실험적이고 독창적인 스타일로 패션계에서 주목받고 있으며, 대중과 패션 열정가들에게 사랑을 받고 있습니다.', 'https://www.miumiu.com/kr/en/fragrances/c/10242KR',
        1,
        now(), now()),
       (21, '디올',
        '1946년 프랑스에서 탄생한 럭셔리 패션 브랜드로, 고급스러운 의류, 액세서리, 향수 등으로 유명합니다. 전통과 현대성이 조화를 이룬 우아하고 세련된 디자인으로 세계적으로 인정받고 있으며, 뛰어난 크리에이티브로 유명합니다.', 'https://shop.dior.co.kr/',
        1,
        now(), now()),
       (22, '돌체앤가바나',
        '1985년 이탈리아에서 시작된 패션 브랜드로, 화려하고 세련된 스타일의 의류와 액세서리로 유명합니다. 대담하고 화려한 패션으로 인기를 끌며, 전통적인 이탈리아 스타일을 현대적으로 표현합니다.', 'https://www.dolcegabbana.com/en-kr/beauty/', 1,
        now(), now()),
       (23, '불가리',
        '1884년 이탈리아에서 탄생한 주얼리 브랜드로, 고급스러운 보석과 액세서리로 유명합니다. 우아하고 현대적인 디자인으로 세계적으로 사랑받으며, 뛰어난 장인 정신과 품질로 손목 위의 예술을 선보입니다.', 'https://www.bulgari.com/en-us/fragrances',
        1,
        now(), now()),
       (24, '러쉬',
        '1995년 영국에서 시작된 신선하고 자연 친화적인 화장품 브랜드로, 합리적인 가격과 천연 재료를 사용하여 제품을 생산합니다. 친환경적이고 창의적인 제품으로 소비자들에게 큰 사랑을 받고 있으며, 동물실험을 하지 않는 브랜드로도 유명합니다.', 'https://weare.lush.co.kr/',
        1, now(), now()),
       (25, '데메테르', '1996년 미국에서 탄생한 브랜드로 주변에서 쉽게 볼 수 있는 자연이나 상황, 기분, 사물등을 향기로 나타내어 소중한 기억을 불러일으키는 향수를 만듭니다.', 'https://demeter.co.kr', 1, now(),
        now()),
       (26, '겔랑',
        '겔랑은 세계에서 가장 오래된 프랑스 향수, 화장품, 스킨케어 브랜드입니다. 많은 전통적인 겔랑 향수는 "겔리나드"라고 알려진 공통 후각 조화를 특징으로 합니다. 이 하우스는 조향사 피에르 프랑수아 파스칼 겔랑(Pierre-François Pascal Guerlain)에 의해 1828년 파리에서 설립되었습니다.', 'https://www.guerlain.com/us/en-us/fragrance/',
        1, now(), now()),
       (27, '4711', '전통적인 독일 향수 브랜드로, 몸과 마음에 활력을 주는 상쾌한 향기로 유명합니다.', 'https://4711online.com/', 1, now(), now()),
       (28, '랑방',
        '1958년 프랑스에서 시작된 전통적인 하이패션 브랜드로, 우아하고 클래식한 스타일의 의류와 액세서리로 유명합니다. 뛰어난 품질과 디자인으로 여성들에게 사랑을 받으며, 시대를 초월한 아름다움을 추구합니다.', 'https://us.lanvin.com/',
        1,
        now(), now()),
       (29, '메종 프란시스 커정',
        '2009년 프랑스에서 시작된 향수 브랜드로, 고급스러운 향수로 유명합니다. 정교하게 조합된 향료와 독특한 블렌딩 기술로 세련된 향수를 선보이며, 높은 품질과 독특한 아트 디렉션으로 주목받고 있습니다.', 'https://www.franciskurkdjian.com/int-en',
        1,
        now(), now()),
       (30, '메모',
        '파리지앵 하우스인 Memo는 여행지에서의 기억을 상상하게 만들어내는 향수를 추구하며 감각적인 향을 만들어냅니다.','https://www.memoparis.com/', 1, now(), now()),
       (31, '딥티크',
        '고대 그리스어로 두 판넬로 이루어진 미술 작품 혹은 조각품이라는 뜻을 가진 딥티크는 고급 원료를 사용한 독특한 향을 만들어냅니다.', 'https://www.diptyqueparis.com/en_us', 1, now(), now()),
       (32, '이솝',
        '"길이 아름답다면 어디로 가는지 묻지 말자"라는 모토를 가지며 식물성 재료와 연구실에서 제조된 성분들을 사용해 세심하게 고안한 지속 가능한 향수를 추구합니다.', 'https://www.aesop.com/kr/', 1, now(), now());

insert into note (id, name, description, created_at, updated_at)
values (1, '씨 솔트', '바삭바삭한 질감, 산뜻함과 순수함을 선사하는 향', now(), now()),
       (2, '세이지', '흙 내음과 향긋한 허브의 향', now(), now()),
       (3, '암브레트', '따뜻하고 부드러운 플로럴 머스크 느낌의 향', now(), now()),
       (4, '페어', '달콤하고 상큼하며 시원한 서양 배의 향', now(), now()),
       (5, '프리지아', '향긋한 꽃 향', now(), now()),
       (6, '파출리', '흙내음과 이끼가 어우러진 어두운 느낌의 허브 향', now(), now()),
       (7, '블랙베리', '달콤한 과즙을 내뿜는 신선한 블랙베리의 향', now(), now()),
       (8, '월계수 잎', '선명함과 눈부신 녹음이 느껴지는 향', now(), now()),
       (9, '시더우드', '카리스마 있는 강렬한 느낌을 주는 우디 향', now(), now()),
       (10, '카시스', '풍부한 과즙과 잘 익은 카시스의 청량한 향', now(), now()),
       (11, '아카시아 허니', '부드럽고 달콤한 비즈왁스와 기분좋은 꿀 향과 만개한 꽃 향의 조화', now(), now()),
       (12, '복숭아', '감미로운 과즙이 가득하고 벨벳 같이 부드러운 향', now(), now()),
       (13, '페티그레인', '쌉쌀한 오렌지 나무의 싱싱함과 깔끔한 광채가 느껴지는 향', now(), now()),
       (14, '라벤더', '장뇌, 꿀, 건초의 상쾌하고 아로마틱한 향', now(), now()),
       (15, '앰버', '우디한 따스함이 있는 관능적인 향', now(), now()),
       (16, '체리', '달콤 상큼한 체리의 싱싱함이 느껴지는 향', now(), now()),
       (17, '진저', '톡 쏘는 생강의 향', now(), now()),
       (18, '자스민', '달콤하고 하얀 꽃들이 가득 넘치는 느낌을 주는 향', now(), now()),
       (19, '머스크', '우디한 따스함이 있는 관능적인 향', now(), now()),
       (20, '핑크 페퍼', '장미빛 뉘앙스가 가미된 신선하고 스파이시한 꽃과 허브의 향', now(), now()),
       (21, '베르가못', '쌉쌀하고 새콤한 과일과 아로마의 복합적인 향', now(), now()),
       (22, '블랙 커런트', '달콤하고 산뜻한, 그리고 약간의 신맛과 풍부한 과일이 어우러지는 향', now(), now()),
       (23, '로즈', '달콤하고 산뜻한, 그리고 약간의 신맛과 풍부한 과일이 어우러지는 향', now(), now()),
       (24, '제라늄', '잎사귀의 녹음과 장밋빛 뉘앙스를 주는 향', now(), now()),
       (25, '다바나', '시트러스 및 허브, 우디 느낌을 주는 따뜻하고 달콤한 향', now(), now()),
       (26, '모스', '유럽의 참나무에서 나는 이끼로부터 얻어내 씁쓸한 냄새가 나는 향', now(), now()),
       (27, '베티베르', '흙내음과 녹음이 은은하게 느껴지는 향', now(), now()),
       (28, '블러드 오렌지', '매우 달콤하고 과즙이 많은 오렌지와 비슷한 향', now(), now()),
       (29, '카다멈', '송진의 아로마틱함과 매콤함이 어우러진 씁쓸한 향', now(), now()),
       (30, '헬리오트로프', '바닐라, 진한 초콜렛의 향기가 은은하게 나는 꽃 향', now(), now()),
       (31, '럼', '럼을 마신 후 숨결에서 느껴지는 씁쓸한 향', now(), now()),
       (32, '꼬냑', '은은한 포도향이 올라오는 꼬냑의 씁쓸한 향', now(), now()),
       (33, '샌달우드', '밀키하고 부드러우며 클래식한 우디 향', now(), now()),
       (34, '벤조인', '바닐라를 연상시키는 따뜻한 발사믹 향', now(), now()),
       (35, '바닐라', '익히 알려진 달콤하고 편안하며 쿠키를 구울 때 나는 향', now(), now()),
       (36, '페루 발삼', '송진의 아로마틱함과 매콤함이 어우러진 씁쓸한 향', now(), now()),
       (37, '통카', '달콤한 과일과 아몬드, 담배를 살짝 섞은듯한 편안한 향', now(), now()),
       (38, '라즈베리', '새콤달콤한 라즈베리 향', now(), now()),
       (39, '스트로베리', '달콤한 딸기 향', now(), now()),
       (40, '블랙커런트', '매우 강렬하고 톡 쏘는 시원한 베리 향', now(), now()),
       (41, '만다린 오렌지', '익히 알고 있는 정석적인 오렌지 향', now(), now()),
       (42, '레몬', '생동감과 경쾌함을 주는 레몬의 향', now(), now()),
       (43, '바이올렛', '달달하고 파우더리한 꽃의 향', now(), now()),
       (44, '유자', '달콤쌉사름하며 톡 쏘는 향', now(), now()),
       (45, '오크모스', '이끼의 초록빛이 느껴지는 독특한 향', now(), now()),
       (46, '리치', '열대 과일의 풍부한 과즙이 워터리하게 느껴지는 향', now(), now()),
       (47, '미모사', '노랗게 물든 미모사 꽃에서 나는 파우더리한 린넨의 향', now(), now()),
       (48, '서양 배', '달달하면서도 시원한 배의 향', now(), now()),
       (49, '사과', '상쾌하고 달콤한 사과 향', now(), now()),
       (50, '세타록스', '앰버그라스를 대체하기 위해 발명된 살짝 짭짤하면서 몽환적이고 포근한 향', now(), now()),
       (51, '이소 이 슈퍼', '부드럽고 무게감 있는 앰버와 비슷한 향', now(), now()),
       (52, '아밀 살리실레이트', '플로럴과 클로버 냄새 사이 어딘가의 향', now(), now()),
       (53, '암브레톨리트', '강한 동물적 느낌과 약간의 과일 향이 섞인 향', now(), now()),
       (54, '헬베톨리드', '머스키함을 기본으로 시원한 배, 잘 마른 나무의 느낌을 지닌 향', now(), now()),
       (55, '올리바넘', '프링킨센스 나무 수액으로 만들어져 우거진 숲의 흙빛과 매콤함이 느껴지는 향', now(), now()),
       (56, '블랙 페퍼', '톡 쏘면서 알싸하고 따뜻한 나무의 향', now(), now()),
       (57, '큐민', '스파이시하면서 따뜻한 느낌을 주는 향', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (1, '우드 세이지 앤 씨 솔트',
        '바람부는 해안을 따라 걸으며 일상을 벗어나보세요. 하얗게 부서지는 파도, 소금기를 머금은 신선한 바다 공기. 험준한 절벽에서 느껴지는 투박한 자연의 향기와 세이지의 우디한 흙 내음이 어우러져 자유롭고 활기찬 에너지와 즐거움이 가득합니다.',
        'EAU_DE_COLOGNE', 1, 3, 'https://www.jomalone.co.kr/product/25946/32241/wood-sage-sea-salt-cologne',
        now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (1, 1, 'TOP', now(), now()),
       (1, 2, 'MIDDLE', now(), now()),
       (1, 3, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (2, '잉글리쉬 페어 앤 프리지아', '가을의 정수. 화이트 프리지아 부케향에 이제 막 익은 배의 신선함을 입히고 호박, 파출리, 우디향으로 은은함을 더했습니다.',
        'EAU_DE_COLOGNE', 1,
        2, 'https://www.jomalone.co.kr/product/25946/12553/english-pear-freesia-cologne', now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (2, 4, 'TOP', now(), now()),
       (2, 5, 'MIDDLE', now(), now()),
       (2, 6, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (3, '블랙베리 앤 베이',
        '순수의 향. 블랙베리를 따던 어린 시절의 추억, 블랙베리로 물든 입술과 손. 이제 막 수확한 월계수 잎의 신선함에 톡 쏘는 블랙베리 과즙을 가미하였습니다. 매력적이고 생기 넘치는 상쾌한 느낌의 향입니다.',
        'EAU_DE_COLOGNE', 1, 1, 'https://www.jomalone.co.kr/product/25946/23540/blackberry-bay-cologne',
        now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (3, 7, 'TOP', now(), now()),
       (3, 8, 'MIDDLE', now(), now()),
       (3, 9, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (4, '넥타린 블로썸 앤 허니',
        '이른 아침 런던 코벤트 가든의 시장. 아카시아 꿀 향기 속에 천도 복숭아, 복숭아, 카씨스와 어린 봄꽃의 향이 녹아있습니다. 달콤하고 유쾌한 향수입니다.',
        'EAU_DE_COLOGNE', 1, 1,
        'https://www.jomalone.co.kr/product/25946/10079/nectarine-blossom-honey-cologne', now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (4, 10, 'TOP', now(), now()),
       (4, 11, 'MIDDLE', now(), now()),
       (4, 12, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (5, '앰버 앤 라벤더',
        '클래식하고 깔끔한 남성 향수. 프렌치 라벤더와 페티그레인은 빛나는 앰버향에 신선함을 더해줍니다. 코스모폴리탄에 어울리는 향수입니다.',
        'EAU_DE_COLOGNE', 1, 3, 'https://www.jomalone.co.kr/product/25946/9947/amber-lavender-cologne',
        now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (5, 13, 'TOP', now(), now()),
       (5, 14, 'MIDDLE', now(), now()),
       (5, 15, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (6, '일렉트릭 체리',
        '상쾌한 첫 봄날처럼 가볍고 상쾌한 향입니다. 부담스럽지 않은 과일 향을 누구나 쉽게 경험해 볼 수 있습니다.',
        'EAU_DE_PARFUM', 5, 1, 'https://www.tomford.com/electric-cherry-eau-de-parfum/TCRM01.html', now(),
        now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (6, 16, 'TOP', now(), now()),
       (6, 17, 'TOP', now(), now()),
       (6, 18, 'MIDDLE', now(), now()),
       (6, 19, 'BASE', now(), now()),
       (6, 20, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (7, '온 어 데이트',
        '늦여름 저녁, 황금 시간대의 석양빛에 흠뻑 빠져 프로방스의 웅장한 포도밭이 내려다보이는 곳에서 하는 데이트를 연상시키는 향수입니다.',
        'EAU_DE_TOILETTE', 2, 1,
        'https://www.maisonmargiela-fragrances.us/fragrances/replica-on-a-date/MM086.html', now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (7, 20, 'TOP', now(), now()),
       (7, 21, 'TOP', now(), now()),
       (7, 22, 'TOP', now(), now()),
       (7, 23, 'MIDDLE', now(), now()),
       (7, 24, 'MIDDLE', now(), now()),
       (7, 25, 'MIDDLE', now(), now()),
       (7, 6, 'BASE', now(), now()),
       (7, 19, 'BASE', now(), now()),
       (7, 26, 'BASE', now(), now()),
       (7, 27, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (8, '비터 피치',
        '나무에서 갓 따낸 탐스러운 복숭아의 달콤함과 벗어날 수 없는 마력을 담은 향수입니다.',
        'EAU_DE_PARFUM', 5, 1, 'https://www.tomford.com/bitter-peach--eau-de-parfum/T941.html', now(),
        now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (8, 12, 'TOP', now(), now()),
       (8, 28, 'TOP', now(), now()),
       (8, 29, 'TOP', now(), now()),
       (8, 30, 'TOP', now(), now()),
       (8, 18, 'MIDDLE', now(), now()),
       (8, 25, 'MIDDLE', now(), now()),
       (8, 31, 'MIDDLE', now(), now()),
       (8, 32, 'MIDDLE', now(), now()),
       (8, 6, 'BASE', now(), now()),
       (8, 33, 'BASE', now(), now()),
       (8, 34, 'BASE', now(), now()),
       (8, 35, 'BASE', now(), now()),
       (8, 36, 'BASE', now(), now()),
       (8, 37, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (9, 'Her', '강렬한 레드와 다크 베리 노트가 밝은 화이트 우디 어코드와 어우러져 조화로운 향을 선사합니다.',
        'EAU_DE_PARFUM', 6, 1, 'https://kr.burberry.com/her-eau-de-parfum-50ml-p40804581', now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (9, 7, 'TOP', now(), now()),
       (9, 16, 'TOP', now(), now()),
       (9, 38, 'TOP', now(), now()),
       (9, 39, 'TOP', now(), now()),
       (9, 40, 'TOP', now(), now()),
       (9, 41, 'TOP', now(), now()),
       (9, 42, 'TOP', now(), now()),
       (9, 18, 'MIDDLE', now(), now()),
       (9, 43, 'MIDDLE', now(), now()),
       (9, 6, 'BASE', now(), now()),
       (9, 15, 'BASE', now(), now()),
       (9, 19, 'BASE', now(), now()),
       (9, 35, 'BASE', now(), now()),
       (9, 45, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (10, '오 로즈', '자칫 지루할 수 있는 장미 향을 변덕스럽고 활기가 넘치며 화려한 면모를 뽐내도록 해석해낸 향수입니다.',
        'EAU_DE_PARFUM', 31, 2, 'https://www.diptyqueparis.com/en_us/p/eau-de-parfum-eau-rose-75ml.html', now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (10, 21, 'TOP', now(), now()),
       (10, 40, 'TOP', now(), now()),
       (10, 46, 'TOP', now(), now()),
       (10, 18, 'MIDDLE', now(), now()),
       (10, 23, 'MIDDLE', now(), now()),
       (10, 24, 'MIDDLE', now(), now()),
       (10, 9, 'BASE', now(), now()),
       (10, 11, 'BASE', now(), now()),
       (10, 19, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (11, '미모사 앤 카다멈',
        '골드빛 미모사의 달콤한 향이 이제 막 으깬 카다멈의 스파이시함 위로 안개처럼 피어 오릅니다. 새벽에 수확한 다마스크 로즈와 파우더리한 헬리토오프 아래 크리미한 통카와 부드러운 샌달우드 향이 물결치듯 퍼져 나가며 따뜻한 천상의 향으로 당신을 매혹시킵니다.',
        'EAU_DE_COLOGNE', 1, 2, 'https://www.jomalone.co.kr/scents/floral/mimosa-cardomom', now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (11, 29, 'TOP', now(), now()),
       (11, 46, 'MIDDLE', now(), now()),
       (11, 37, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (12, '어나더 13',
        '13개의 원자재로 향을 추출해 뇌쇄적이면서도 유니크한 암브록스 베이스 머스크 노트의 중독성 강한 묘약에 자스민, 모스 등 세심하게 선정된 성분들로 강렬함과 매혹을 더해 구성됩니다.',
        'EAU_DE_PARFUM', 16, 11, 'https://www.lelabofragrances.co.kr/another-13-747.html', now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (12, 48, 'TOP', now(), now()),
       (12, 49, 'TOP', now(), now()),
       (12, 3, 'MIDDLE', now(), now()),
       (12, 18, 'MIDDLE', now(), now()),
       (12, 26, 'MIDDLE', now(), now()),
       (12, 52, 'MIDDLE', now(), now()),
       (12, 50, 'BASE', now(), now()),
       (12, 51, 'BASE', now(), now()),
       (12, 53, 'BASE', now(), now()),
       (12, 54, 'BASE', now(), now());

insert into perfume (id, name, story, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
values (13, '이더시스',
        '이더시스는 거울 표면 너머에 있는 상상의 세계를 기립니다. 다양하게 빛나는 워터리한 플로럴 노트가 따뜻한 스파이시의 우디 베이스와 만나 연못이나 거울에서 느껴지는 물의 고요함을 연상시킵니다.',
        'EAU_DE_PARFUM', 32, 8, 'https://www.lelabofragrances.co.kr/another-13-747.html', now(), now());

insert into perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
values (13, 13, 'TOP', now(), now()),
       (13, 55, 'TOP', now(), now()),
       (13, 56, 'TOP', now(), now()),
       (13, 9, 'MIDDLE', now(), now()),
       (13, 55, 'MIDDLE', now(), now()),
       (13, 57, 'MIDDLE', now(), now()),
       (13, 9, 'BASE', now(), now()),
       (13, 27, 'BASE', now(), now()),
       (13, 33, 'BASE', now(), now());

insert into review (day_type, season, strength, duration, short_review, full_review, user_id, perfume_id, created_at,
                    updated_at)
values ('DAILY', 'SPRING', 'MODERATE', 'SHORT', '달달한 향이라 너무 좋음', '딱 봄에 벚꽃놀이갈 때 뿌리면 진짜 좋을 것 같은 향수에요!', 1, 6, now(),
        now()),
       ('SPECIAL', 'SPRING', 'HEAVY', 'LONG', '체리 향이 기가 막힘', '비싸긴 한데 돈값하는 향수.. 그치만 비싸서 아무때나 못뿌릴 것 같아요', 1, 6, now(),
        now()),
       ('DAILY', 'SPRING', 'HEAVY', 'LONG', '굿이에용굿이에용굿이에용', '', 1, 6, now(), now()),
       ('TRAVEL', 'SPRING', 'HEAVY', 'LONG', '아주 맘에 들어요', '평소에 체리 완전 좋아하는데 시향해보고 너무 만족스러워서 구매했어요!', 1, 6, now(), now()),
       ('SPECIAL', 'SPRING', 'MODERATE', 'LONG', '난별로난별로난별로', '생각보다는 별로', 1, 6, now(), now()),
       ('DAILY', 'SPRING', 'MODERATE', 'SHORT', '니치 향수의 끝판왕 브랜드답다', '역시 톰포드', 1, 6, now(), now()),
       ('SPECIAL', 'SPRING', 'MODERATE', 'MEDIUM', '썸남이 무슨 향수냐고 물어봄', '아직 대답은 안해줌', 1, 6, now(), now());

insert into tag (id, name, created_at, updated_at)
values (1, '자연스러운', now(), now()),
       (2, '달콤한', now(), now()),
       (3, '상큼한', now(), now()),
       (4, '은은한', now(), now()),
       (5, '우아한', now(), now()),
       (6, '깔끔한', now(), now()),
       (7, '고급진', now(), now()),
       (8, '시크한', now(), now()),
       (9, '섹시한', now(), now()),
       (10, '독특한', now(), now());

insert into perfume_theme (id, title, content, thumbnail_id, perfume_ids, created_at, updated_at)
values (1, '한겨울의 향기', '엄동설한에도 포근한 분위기를 선사하는 향수를 확인해보세요.', 1, '9,10,11,12', now(), now());

insert into file(id, url, user_id, created_at, updated_at)
values (1, 'https://picsum.photos/200/300', 1, now(), now());
