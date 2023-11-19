INSERT INTO member (id, username, email, password, role, promotion_consent, marketing_consent, created_at, updated_at)
VALUES (100, 'admin', 'admin@admin.com', '$2a$10$rdTvUNYrCFRv7zClDfLhpOn2XegwzJhXRKGFdz/NEtXNKXmTxlniK', 'USER', 0, 0,
        now(), now());

INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (1, '프루티', '달콤한 과일의 향이 지속되어 생동감과 매력적인 느낌을 줍니다.', now(), now());
INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (2, '플로럴', '꽃 향기를 가득 담아 사랑스러운 느낌을 줍니다.', now(), now());
INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (3, '우디', '빽빽한 나무들이 있는 숲의 신선하고 무거운 향이 중후하고 따뜻한 느낌을 줍니다.', now(), now());
INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (4, '시트러스', '상큼하고 톡 쏘는 향으로 가볍고 산뜻한 느낌을 줍니다.', now(), now());
INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (5, '스위트', '달달하고 크리미한 향으로 가볍고 중독적인 느낌을 줍니다.', now(), now());
INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (6, '그린', '자연의 풀내음 향을 담아 싱그럽고 고급스러운 느낌을 줍니다.', now(), now());
INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (7, '머스크', '포근하고 부드러운 향으로 관능적인 느낌을 줍니다.', now(), now());
INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (8, '스파이시', '매운듯한 자극적인 향으로 깊이감과 매혹적인 느낌을 줍니다.', now(), now());
INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (9, '오리엔탈', '동양적인 향으로 성숙하고 강렬한 느낌을 줍니다.', now(), now());
INSERT INTO category (id, name, description, created_at, updated_at)
VALUES (10, '아쿠아', '맑고 깨끗한 물의 향으로 상쾌하고 청량감 있는 느낌을 줍니다.', now(), now());

INSERT INTO brand (id, name, story, created_at, updated_at)
values (1, '조말론', '조말론은 18세기 영국에서 시작된 럭셔리 향수 브랜드입니다. 영국 왕실의 향수 제조사로 시작하여, 현재는 럭셔리 향수 브랜드로 자리잡고 있습니다.', now(), now());
INSERT INTO brand (id, name, story, created_at, updated_at)
values (2, '메종 마르지엘라', '메종 마르지엘라는 1988년 파리에서 설립된 프랑스 패션 하우스입니다. 특유의 미니멀한 디자인과 독특한 향기로 많은 사랑을 받고 있습니다.', now(), now());
INSERT INTO brand (id, name, story, created_at, updated_at)
values (3, '바이레도', '바이레도는 1996년 이탈리아에서 시작된 럭셔리 향수 브랜드입니다. 바이레도는 이탈리아의 전통적인 향수 제조법을 바탕으로, 향기와 디자인에 집중하여 제품을 만들어내고 있습니다.',
        now(), now());
INSERT INTO brand (id, name, story, created_at, updated_at)
values (4, '샤넬', '샤넬은 1910년 프랑스에서 시작된 럭셔리 패션 하우스이며, 향수 브랜드로도 유명하죠. 샤넬 No.5는 세계에서 가장 유명한 향수 중 하나입니다.', now(), now());

INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (1, '씨 솔트', '바삭바삭한 질감, 산뜻함과 순수함을 선사하는 향', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (2, '세이지', '흙 내음과 향긋한 허브의 향', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (3, '암브레트', '따뜻하고 부드러운 플로럴 머스크 느낌의 향', now(), now());

INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (4, '페어', '달콤하고 상큼하며 시원한 서양 배의 향', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (5, '프리지아', '향긋한 꽃 향', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (6, '파출리', '흙내음과 이끼가 어우러진 어두운 느낌의 허브 향', now(), now());

INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (7, '블랙베리', '과즙을 내뿜는 달콤한 블랙커런트 향', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (8, '월계수 잎', '선명함과 눈부신 녹음이 느껴지는 향', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (9, '시더우드', '카리스마 있는 강렬한 느낌을 주는 우디 향', now(), now());

INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (10, '카시스', '풍부한 과즙과 잘 익은 카시스의 청량한 향', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (11, '아카시아 허니', '부드럽고 달콤한 비즈왁스와 기분좋은 꿀 향과 만개한 꽃 향의 조화', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (12, '복숭아', '감미로운 과즙이 가득하고 벨벳 같이 부드러운 향', now(), now());

INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (13, '프티그레인', '쌉쌀한 오렌지 나무의 싱싱함과 깔끔한 광채가 느껴지는 향', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (14, '라벤더', '장뇌, 꿀, 건초의 상쾌하고 아로마틱한 향', now(), now());
INSERT INTO note (id, name, description, created_at, updated_at)
VALUES (15, '앰버', '우디한 따스함이 있는 관능적인 향', now(), now());

INSERT INTO perfume (id, name, story, capacity, price, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
VALUES (1, '우드 세이지 앤 씨 솔트',
        '바람부는 해안을 따라 걸으며 일상을 벗어나보세요. 하얗게 부서지는 파도, 소금기를 머금은 신선한 바다 공기. 험준한 절벽에서 느껴지는 투박한 자연의 향기와 세이지의 우디한 흙 내음이 어우러져 자유롭고 활기찬 에너지와 즐거움이 가득합니다.',
        30, 110000, 'EAU_DE_COLOGNE', 1, 3, 'https://www.jomalone.co.kr/product/25946/32241/wood-sage-sea-salt-cologne', now(), now());

INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (1, 1, 'TOP', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (1, 2, 'MIDDLE', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (1, 3, 'BASE', now(), now());

INSERT INTO perfume (id, name, story, capacity, price, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
VALUES (2, '잉글리쉬 페어 앤 프리지아', '가을의 정수. 화이트 프리지아 부케향에 이제 막 익은 배의 신선함을 입히고 호박, 파출리, 우디향으로 은은함을 더했습니다.', 30, 110000, 'EAU_DE_COLOGNE', 1,
        2, 'https://www.jomalone.co.kr/product/25946/12553/english-pear-freesia-cologne', now(), now());

INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (2, 4, 'TOP', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (2, 5, 'MIDDLE', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (2, 6, 'BASE', now(), now());

INSERT INTO perfume (id, name, story, capacity, price, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
VALUES (3, '블랙베리 앤 베이',
        '순수의 향. 블랙베리를 따던 어린 시절의 추억, 블랙베리로 물든 입술과 손. 이제 막 수확한 월계수 잎의 신선함에 톡 쏘는 블랙베리 과즙을 가미하였습니다. 매력적이고 생기 넘치는 상쾌한 느낌의 향입니다.',
        30, 110000, 'EAU_DE_COLOGNE', 1, 1, 'https://www.jomalone.co.kr/product/25946/23540/blackberry-bay-cologne', now(), now());

INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (3, 7, 'TOP', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (3, 8, 'MIDDLE', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (3, 9, 'BASE', now(), now());

INSERT INTO perfume (id, name, story, capacity, price, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)
VALUES (4, '넥타린 블로썸 앤 허니',
        '이른 아침 런던 코벤트 가든의 시장. 아카시아 꿀 향기 속에 천도 복숭아, 복숭아, 카씨스와 어린 봄꽃의 향이 녹아있습니다. 달콤하고 유쾌한 향수입니다.',
        30, 110000, 'EAU_DE_COLOGNE', 1, 1, 'https://www.jomalone.co.kr/product/25946/10079/nectarine-blossom-honey-cologne', now(), now());

INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (3, 10, 'TOP', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (3, 11, 'MIDDLE', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (3, 12, 'BASE', now(), now());

INSERT INTO perfume (id, name, story, capacity, price, concentration, brand_id, category_id, perfume_shop_url,
                     created_at, updated_at)

VALUES (5, '앰버 앤 라벤더',
        '클래식하고 깔끔한 남성 향수. 프렌치 라벤더와 페티그레인은 빛나는 앰버향에 신선함을 더해줍니다. 코스모폴리탄에 어울리는 향수입니다.',
        100, 220000, 'EAU_DE_COLOGNE', 1, 3, 'https://www.jomalone.co.kr/product/25946/9947/amber-lavender-cologne', now(), now());

INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (3, 13, 'TOP', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (3, 14, 'MIDDLE', now(), now());
INSERT INTO perfume_note (perfume_id, note_id, note_level, created_at, updated_at)
VALUES (3, 15, 'BASE', now(), now());
