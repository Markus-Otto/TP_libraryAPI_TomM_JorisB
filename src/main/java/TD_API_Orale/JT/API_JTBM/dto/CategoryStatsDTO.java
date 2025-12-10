package TD_API_Orale.JT.API_JTBM.dto;

import TD_API_Orale.JT.API_JTBM.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryStatsDTO {
    private Genre category;
    private Long count;
}
