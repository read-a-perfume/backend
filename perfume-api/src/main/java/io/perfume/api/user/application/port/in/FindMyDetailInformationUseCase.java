package io.perfume.api.user.application.port.in;

import io.perfume.api.user.adapter.in.http.dto.FindMyDetailInformationResponseDto;

public interface FindMyDetailInformationUseCase {

    /**
     * 자신의 자세한 정보를 반환
     *
     * @param accessToken 자신의 정보를 찾을 때 필요한 값이 포함
     * @return
     */
    FindMyDetailInformationResponseDto findMyDetailInformation(String accessToken);
}
