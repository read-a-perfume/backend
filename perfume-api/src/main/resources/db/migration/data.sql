insert into member (id, sex, username, email, password, role, promotion_consent, marketing_consent, created_at,
                    updated_at)
values (100, 'OTHER', 'admin', 'admin@admin.com', '$2a$10$JqiVSGED0ceSJj7qMwXRmO8Otam7IrvqY4j6K.mklPDgo54SP49sa',
        'USER', 0, 0,
        now(), now());

insert into category (id, name, description, tags, created_at, updated_at)
values (1, '프루티', '달콤한 과일의 향이 지속되어 생동감과 매력적인 느낌을 줍니다.', '#달달한 #과즙미', now(), now()),
       (2, '플로럴', '꽃 향기를 가득 담아 사랑스러운 느낌을 줍니다.', '#우아한 #사랑스러운 #꽃향기', now(), now()),
       (3, '우디', '나무 향을 의미하며, 건조하고 성숙한 느낌을 전달합니다.', '#차분함 #릴렉스', now(), now()),
       (4, '시트러스', '상큼하고 톡 쏘는 향으로 가볍고 산뜻한 느낌을 줍니다.', '#상큼한 #질리지않는', now(), now()),
       (5, '스위트', '달달하고 크리미한 향으로 가볍고 중독적인 느낌을 줍니다.', '#달달함 #대중적 #행복감', now(), now()),
       (6, '그린', '자연의 풀내음 향을 담아 싱그럽고 고급스러운 느낌을 줍니다.', '#자연스러운 #싱그러움', now(), now()),
       (7, '머스크', '포근하고 부드러운 향으로 관능적인 느낌을 줍니다.', '#이불속 #포근한 #따뜻한', now(), now()),
       (8, '스파이시', '매운듯한 자극적인 향으로 깊이감과 매혹적인 느낌을 줍니다.', '#톡쏘는 #독특함', now(), now()),
       (9, '오리엔탈', '동양적인 향으로 성숙하고 강렬한 느낌을 줍니다.', '#동양미 #성숙함', now(), now()),
       (10, '아쿠아', '맑고 깨끗한 물의 향으로 상쾌하고 청량감 있는 느낌을 줍니다.', '#시원한 #여름휴가', now(), now()),
       (11, '애니멀', '동물적인 느낌 혹은 원초적인 본능을 이끌어내는 향으로 따뜻한 느낌을 줍니다.', '#독특함 #중독적 #이끌림', now(), now());

insert into brand (id, name, story, created_at, updated_at)
values (1, '조말론', '조말론은 18세기 영국에서 시작된 럭셔리 향수 브랜드입니다. 영국 왕실의 향수 제조사로 시작하여, 현재는 럭셔리 향수 브랜드로 자리잡고 있습니다.', now(), now()),
       (2, '메종 마르지엘라', '메종 마르지엘라는 1988년 파리에서 설립된 프랑스 패션 하우스입니다. 특유의 미니멀한 디자인과 독특한 향기로 많은 사랑을 받고 있습니다.', now(), now()),
       (3, '바이레도', '바이레도는 1996년 이탈리아에서 시작된 럭셔리 향수 브랜드입니다. 바이레도는 이탈리아의 전통적인 향수 제조법을 바탕으로, 향기와 디자인에 집중하여 제품을 만들어내고 있습니다.',
        now(), now()),
       (4, '샤넬', '샤넬은 1910년 프랑스에서 시작된 럭셔리 패션 하우스이며, 향수 브랜드로도 유명하죠. 샤넬 No.5는 세계에서 가장 유명한 향수 중 하나입니다.', now(), now()),
       (5, '톰 포드', '21세기 진정한 럭셔리 브랜드를 추구하는 톰 포드의 향수는 고급스럽고 독특한 향으로 유명하며, 세련된 디자인과 높은 품질로 유명합니다.', now(), now());

insert into note (id, name, description, created_at, updated_at)
values (1, '씨 솔트', '바삭바삭한 질감, 산뜻함과 순수함을 선사하는 향', now(), now()),
       (2, '세이지', '흙 내음과 향긋한 허브의 향', now(), now()),
       (3, '암브레트', '따뜻하고 부드러운 플로럴 머스크 느낌의 향', now(), now()),
       (4, '페어', '달콤하고 상큼하며 시원한 서양 배의 향', now(), now()),
       (5, '프리지아', '향긋한 꽃 향', now(), now()),
       (6, '파출리', '흙내음과 이끼가 어우러진 어두운 느낌의 허브 향', now(), now()),
       (7, '블랙베리', '과즙을 내뿜는 달콤한 블랙커런트 향', now(), now()),
       (8, '월계수 잎', '선명함과 눈부신 녹음이 느껴지는 향', now(), now()),
       (9, '시더우드', '카리스마 있는 강렬한 느낌을 주는 우디 향', now(), now()),
       (10, '카시스', '풍부한 과즙과 잘 익은 카시스의 청량한 향', now(), now()),
       (11, '아카시아 허니', '부드럽고 달콤한 비즈왁스와 기분좋은 꿀 향과 만개한 꽃 향의 조화', now(), now()),
       (12, '복숭아', '감미로운 과즙이 가득하고 벨벳 같이 부드러운 향', now(), now()),
       (13, '프티그레인', '쌉쌀한 오렌지 나무의 싱싱함과 깔끔한 광채가 느껴지는 향', now(), now()),
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
       (30, '헬리오트로프', '송진의 아로마틱함과 매콤함이 어우러진 씁쓸한 향', now(), now()),
       (31, '럼', '송진의 아로마틱함과 매콤함이 어우러진 씁쓸한 향', now(), now()),
       (32, '꼬냑', '송진의 아로마틱함과 매콤함이 어우러진 씁쓸한 향', now(), now()),
       (33, '샌달우드', '밀키하고 부드러우며 클래식한 우디 향', now(), now()),
       (34, '벤조인', '바닐라를 연상시키는 따뜻한 발사믹 향', now(), now()),
       (35, '바닐라', '익히 알려진 달콤하고 편안하며 쿠키를 구울 때 나는 향', now(), now()),
       (36, '페루 발삼', '송진의 아로마틱함과 매콤함이 어우러진 씁쓸한 향', now(), now()),
       (37, '통카', '달콤한 과일과 아몬드, 담배를 살짝 섞은듯한 편안한 향', now(), now());

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