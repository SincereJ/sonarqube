/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.api.server.ws.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.api.server.ws.WebService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidatingRequestTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void required_param_is_null_in_std_param_method() {

    String param_key = "required_param";
    String param_value = "param_value";

    WebService.Param param = mock(WebService.Param.class);
    when(param.key()).thenReturn(param_key);
    when(param.possibleValues()).thenReturn(null);
    when(param.maximumLength()).thenReturn(null);
    when(param.maximumValue()).thenReturn(null);

    when(param.isRequired()).thenReturn(true);

    WebService.Action action = mock(WebService.Action.class);
    when(action.param(param_key)).thenReturn(param);

    ValidatingRequest underTest = mock(ValidatingRequest.class, CALLS_REAL_METHODS);
    underTest.setAction(action);
    when(underTest.readParam(param_key)).thenReturn(null);

    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("The 'required_param' parameter is missing");

    String result = underTest.param(param_key);

    assertThat(result).isEqualTo(param_value);

  }

}
